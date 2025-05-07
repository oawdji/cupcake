package com.example.cupcake.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.cupcake.R
import androidx.compose.material.icons.filled.ArrowDropDown

@Composable
fun AddAddressScreen(
    onNextButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // 定义省份列表
    val provinces = listOf("北京市", "上海市", "广东省", "江苏省", "浙江省")
    var expanded by remember { mutableStateOf(false) }
    var selectedProvince by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    // 检查地址信息是否都已填写
    val isAddressValid = selectedProvince.isNotBlank() && address.isNotBlank()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(R.string.add_address),
                style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
            )

            // 省份选择下拉菜单
            OutlinedTextField(
                value = selectedProvince,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.province_label)) },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    androidx.compose.material3.IconButton(onClick = { expanded = true }) {
                        androidx.compose.material3.Icon(
                            androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                provinces.forEach { province ->
                    DropdownMenuItem(
                        text = { Text(province) },
                        onClick = {
                            selectedProvince = province
                            expanded = false
                        }
                    )
                }
            }

            // 详细地址输入框
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text(stringResource(R.string.address_label)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
        Button(
            onClick = {
                val fullAddress = "$selectedProvince $address"
                onNextButtonClicked(fullAddress)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_medium)),
            // 根据地址信息是否有效来启用或禁用按钮
            enabled = isAddressValid
        ) {
            Text(stringResource(R.string.next))
        }
    }
}