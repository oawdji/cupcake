/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake.data

import com.example.cupcake.R

object DataSource {
    val flavors = listOf(
        R.string.vanilla,
        R.string.chocolate,
        R.string.red_velvet,
        R.string.salted_caramel,
        R.string.coffee
    )

    val quantityOptions = listOf(
        Triple(1, R.string.americano, 9.9),
        Triple(2, R.string.latte, 13.9),
        Triple(3, R.string.cappuccino, 14.9)
    )

    val sweet = listOf(
        R.string.nosugar,
        R.string.triple_sugar,
        R.string.five_point_sugar,
        R.string.seven_point_sugar,
        R.string.full_sugar
    )
}
