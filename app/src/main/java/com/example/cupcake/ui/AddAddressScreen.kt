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

// filePath：cupcake/ui/AddAddressScreen.kt
@Composable
fun AddAddressScreen(
    onNextButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // 定义省份列表
    val provinces = listOf("北京市", "上海市", "广东省", "江苏省", "浙江省")
    var expandedProvince by remember { mutableStateOf(false) }
    var selectedProvince by remember { mutableStateOf("") }

    // 根据选择的省份动态获取城市列表
    val cities = when (selectedProvince) {
        "北京市" -> listOf("东城区", "西城区", "朝阳区", "海淀区", "丰台区", "石景山区", "门头沟区", "房山区", "通州区", "顺义区", "昌平区", "大兴区", "怀柔区", "平谷区", "密云区", "延庆区")
        "上海市" -> listOf("黄浦区", "徐汇区", "长宁区", "静安区", "普陀区", "虹口区", "杨浦区", "闵行区", "宝山区", "嘉定区", "浦东新区", "金山区", "松江区", "青浦区", "奉贤区", "崇明区")
        "广东省" -> listOf("广州市", "深圳市", "佛山市", "东莞市", "中山市", "珠海市", "江门市", "肇庆市", "惠州市", "汕头市", "潮州市", "揭阳市", "汕尾市", "湛江市", "茂名市", "阳江市", "云浮市", "韶关市", "清远市", "梅州市", "河源市")
        "江苏省" -> listOf("南京市", "无锡市", "徐州市", "常州市", "苏州市", "南通市", "连云港市", "淮安市", "盐城市", "扬州市", "镇江市", "泰州市", "宿迁市")
        "浙江省" -> listOf("杭州市", "宁波市", "温州市", "绍兴市", "湖州市", "嘉兴市", "金华市", "衢州市", "台州市", "丽水市", "舟山市")
        else -> emptyList()
    }
    var expandedCity by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf("") }

    var address by remember { mutableStateOf("") }

    // 检查地址信息是否都已填写
    val isAddressValid = selectedProvince.isNotBlank() && selectedCity.isNotBlank() && address.isNotBlank()

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
                    androidx.compose.material3.IconButton(onClick = { expandedProvince = true }) {
                        androidx.compose.material3.Icon(
                            androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                }
            )
            DropdownMenu(
                expanded = expandedProvince,
                onDismissRequest = { expandedProvince = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                provinces.forEach { province ->
                    DropdownMenuItem(
                        text = { Text(province) },
                        onClick = {
                            selectedProvince = province
                            selectedCity = "" // 重置城市选择
                            expandedProvince = false
                        }
                    )
                }
            }

            // 城市选择下拉菜单（只有选择了省份后才显示）
            if (selectedProvince.isNotBlank()) {
                OutlinedTextField(
                    value = selectedCity,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.city_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        androidx.compose.material3.IconButton(onClick = { expandedCity = true }) {
                            androidx.compose.material3.Icon(
                                androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                                contentDescription = null
                            )
                        }
                    }
                )
                DropdownMenu(
                    expanded = expandedCity,
                    onDismissRequest = { expandedCity = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    cities.forEach { city ->
                        DropdownMenuItem(
                            text = { Text(city) },
                            onClick = {
                                selectedCity = city
                                expandedCity = false
                            }
                        )
                    }
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
                val fullAddress = "$selectedProvince $selectedCity $address"
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