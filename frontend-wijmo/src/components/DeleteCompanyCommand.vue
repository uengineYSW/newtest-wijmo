<template>

    <v-card outlined>
        <v-card-title>
            DeleteCompany
        </v-card-title>

        <v-card-text>
            <UUID offline label="CompanyId" v-model="value.companyId" :editMode="editMode" @change="change"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="deleteCompany"
            >
                DeleteCompany
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
        name: 'DeleteCompanyCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.companyId = {};
        },
        watch: {
        },
        methods: {
            deleteCompany() {
                this.$emit('deleteCompany', this.value);
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

