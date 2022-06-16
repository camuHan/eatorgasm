package com.cason.eatorgasm.component.contract

import com.cason.eatorgasm.define.CMEnum

interface ComponentContract {
    fun onCommand(commandType: CMEnum.EatCommand, vararg args: Any?)
}