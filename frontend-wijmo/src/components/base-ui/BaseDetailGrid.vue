<template>
    <div></div>
</template>
 
 <script>
     import BaseGrid from '../base-ui/BaseGrid';
 
     export default {
         name: 'base-detail-grid',
         mixins:[BaseGrid],
         
         async created() {
             if (!this.value) {
                 this.value = [];
             }
         },
         methods: {
         }
     };
     
 </script>
 
 