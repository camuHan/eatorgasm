package com.cason.eatorgasm.adapter

import com.cason.eatorgasm.define.CMEnum

interface BoardContract {
    fun onCommand(commandType: CMEnum.EatCommand, vararg args: Any?)
}