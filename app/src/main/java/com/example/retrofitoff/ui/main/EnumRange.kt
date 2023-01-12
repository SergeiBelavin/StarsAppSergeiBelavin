package com.example.retrofitoff.ui.main

class EnumRange {
    companion object {
        fun groupsType(groupType: GroupType): Int {
            return groupType.numInt
        }
        enum class GroupType(val numInt: Int) {
            WEEK(14),
            MONTH(30),
            YEAR(60),
        }
    }
}