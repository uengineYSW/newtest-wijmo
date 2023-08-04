<template>

    <v-card outlined>
        <v-card-title>
            CreateSalesOrder
        </v-card-title>

        <v-card-text>
            <String label="SalesPerson" v-model="value.salesPerson" :editMode="editMode"/>
            <String label="CompanyId" v-model="value.companyId" :editMode="editMode"/>
            <SalesType offline label="SalesType" v-model="value.salesType" :editMode="editMode" @change="change"/>
            <SalesItemList offline label="SalesItems" v-model="value.salesItems" :editMode="editMode" @change="change"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="createSalesOrder"
            >
                CreateSalesOrder
            </v-btn>
            
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>
   
    export default {
        name: 'CreateSalesOrderCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.salesPerson = '';
            this.value.companyId = '';
            this.value.salesType = {};
            this.value.salesItems = [];
        },
        watch: {
        },
        methods: {
            createSalesOrder() {
                this.$emit('createSalesOrder', this.value);
            },
            close() {
                this.$emit('closeDialog');
            },
            change() {
                this.$emit('input', this.value);
            },
        }
    }
</script>

