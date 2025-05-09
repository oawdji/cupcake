package com.example.cupcake.ui

import androidx.lifecycle.ViewModel
import com.example.cupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.cupcake.data.DataSource

/** Price for a single cupcake */
private const val PRICE_PER_CUPCAKE = 2.00

/** Additional cost for same day pickup of an order */
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

/**
 * [OrderViewModel] holds information about a cupcake order in terms of quantity, flavor,
 * pickup date and address. It also knows how to calculate the total price based on these order details.
 */
class OrderViewModel : ViewModel() {

    /**
     * Cupcake state for this order
     */
    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    /**
     * Set the quantity [numberCupcakes] of cupcakes for this order's state and update the price
     */
    fun setQuantity(productId: Int) {
        // 从数据源中获取对应的价格
        val price = DataSource.quantityOptions.firstOrNull { it.first == productId }?.third ?: 0.0
        _uiState.update { currentState ->
            currentState.copy(
                quantity = productId, // 这里quantity存储产品ID而不是数量
                price = calculatePrice(productId = productId)
            )
        }
    }

    /**
     * Set the [desiredFlavor] of cupcakes for this order's state.
     * Only 1 flavor can be selected for the whole order.
     */
    fun setFlavor(desiredFlavor: String) {
        _uiState.update { currentState ->
            currentState.copy(flavor = desiredFlavor)
        }
    }

    /**
     * Set the [pickupDate] for this order's state and update the price
     */
    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                date = pickupDate,
                price = calculatePrice(pickupDate = pickupDate)
            )
        }
    }

    fun setSweet(sweet: String) {
        _uiState.update { currentState ->
            currentState.copy(
                sweet = sweet
            )
        }
    }

    /**
     * Set the [address] for this order's state.
     */
    fun setAddress(address: String) {
        _uiState.update { currentState ->
            currentState.copy(address = address)
        }
    }

    /**
     * Reset the order state
     */
    fun resetOrder() {
        _uiState.value = OrderUiState(pickupOptions = pickupOptions())
    }

    /**
     * Returns the calculated price based on the order details.
     */
    private fun calculatePrice(
        productId: Int = _uiState.value.quantity, // 产品ID
        pickupDate: String = _uiState.value.date,
    ): String {
        // 从数据源中获取产品价格
        val basePrice = DataSource.quantityOptions.firstOrNull { it.first == productId }?.third ?: 0.0
        // 计算总价
        var totalPrice = basePrice
        return NumberFormat.getCurrencyInstance().format(totalPrice)
    }

    /**
     * Returns a list of date options starting with the current date and the following 3 dates.
     */
    // filePath：cupcake/ui/OrderViewModel.kt
    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        // 使用中文环境
        val locale = Locale("zh", "CN")
        val formatter = SimpleDateFormat("EEEE M月d日", locale)
        val calendar = Calendar.getInstance()

        // 添加当前日期和接下来的7天
        repeat(8) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }
}
