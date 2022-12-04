package com.example.retrofitoff.ui.chart

class EnumRange {
    companion object {
        fun groupsType(groupType: GroupType): Int {
            return groupType.numInt
        }
        enum class GroupType(val numInt: Int) {
            FOURTEEN_DAYS(14),
            THIRTY_DAYS(30),
            SIXTY_DAYS(60),
        }
    }
}