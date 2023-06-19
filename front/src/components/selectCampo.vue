<template lang="pug">
div
    #select-obrigatorio
    validation-provider(
        :rules="rules"
        v-slot="erros"
        :name="name"
    )
        label.required {{ label }}
        el-select.input(
            v-model="values",
            clearanle
            @clear="clearSelect"
            filterable
            style= "width: 100%"
            
        )
</template>