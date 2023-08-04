<template>

    <v-data-table
        :headers="headers"
        :items="company"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'CompanyView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            company : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/companies'))

            temp.data._embedded.companies.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.company = temp.data._embedded.companies;
        },
        methods: {
        }
    }
</script>

