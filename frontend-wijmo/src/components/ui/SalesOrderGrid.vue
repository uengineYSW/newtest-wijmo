<template>
    <div style="max-height:80vh;">
        <div class="gs-bundle-of-buttons" style="max-height:10vh;">
            <v-btn @click="addNewRow" small color="primary">
                <v-icon small>mdi-plus-circle-outline</v-icon>등록
            </v-btn>
            <v-btn  @click="editSelectedRow" small color="primary">
                <v-icon small>mdi-pencil</v-icon>수정
            </v-btn>
            <v-btn @click="deleteSelectedRows" small color="primary">
                <v-icon small>mdi-minus-circle-outline</v-icon>삭제
            </v-btn>
            <excel-export-button :exportService="this.exportService" :getFlex="getFlex" />
        </div>

        <!-- the grid -->
        <wj-flex-grid
            ref="flexGrid"
            :key="value.length"
            :autoGenerateColumns="false"
            :allowAddNew="false"
            :allowDelete="true"
            :allowPinning="'SingleColumn'"
            :newRowAtTop="false"
            :showMarquee="true"
            :selectionMode="'MultiRange'"
            :validateEdits="false"
            :itemsSource="value"
            :initialized="flexInitialized"
            :selectionChanged="onSelectionChanged"
            style="margin-top:10px; max-height:65vh;"
            class="wj-felx-grid"
        >
            <wj-flex-grid-filter :filterColumns="['salesOrderNumber','salesPerson','companyId','salesType','salesItems',]" />
            <wj-flex-grid-cell-template cellType="RowHeader" v-slot="cell">{{cell.row.index + 1}}</wj-flex-grid-cell-template>
            <wj-flex-grid-column binding="salesOrderNumber" header="수주 번호" width="2*" :isReadOnly="true" align="center" />
            <wj-flex-grid-column binding="salesPerson" header="수주 담당자" width="2*" :isReadOnly="true" align="center" />
            <wj-flex-grid-column binding="companyId" header="수주 회사" width="2*" :isReadOnly="true" align="center" />
            <wj-flex-grid-column binding="salesType" header="수주 종류" width="2*" :isReadOnly="true" align="center" />
            <wj-flex-grid-column binding="companyId" header="회사" width="2*" :isReadOnly="true" align="center">
                <wj-flex-grid-cell-template cellType="Cell" v-slot="cell">   
                    <companyId :editMode="false" v-model="cell.item.companyId"></companyId>
                </wj-flex-grid-cell-template>
            </wj-flex-grid-column>
        </wj-flex-grid>
        <SalesItemsDetailGrid offline v-if="selectedRow" v-model="selectedRow.salesItem" />
        <v-col>
            <v-dialog
                v-model="openDialog"
                transition="dialog-bottom-transition"
                width="35%"
                class="add-edit-dialog"
            >
                <template>
                    <v-card>
                        <v-toolbar
                            color="primary"
                            class="elevation-0"
                            height="50px"
                        >
                            <div style="color:white; font-size:17px; font-weight:700;">수주 등록</div>
                            <v-spacer></v-spacer>
                            <v-icon
                                color="white"
                                small
                                @click="openDialog = false"
                            >mdi-close</v-icon>
                        </v-toolbar>
                        <v-card-text>
                        <div>
                            <SalesOrder :offline="offline"
                                :isNew="!itemToEdit"
                                :editMode="true"
                                v-model="itemToEdit"
                                @add="append"
                            />
                        </div>
                        </v-card-text>
                    </v-card>
                </template>
            </v-dialog>
        </v-col>
        <v-col style="margin-bottom:40px;">
            <div class="text-center">
                <v-dialog
                    width="332.5"
                    fullscreen
                    hide-overlay
                    transition="dialog-bottom-transition"
                >
                    <v-btn
                        style="position:absolute; top:2%; right:2%"
                        @click="closeDialog()"
                        depressed
                        icon 
                        absolute
                    >
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                </v-dialog>
            </div>
        </v-col>
    </div>
</template>

<script>
import SalesOrder from '../SalesOrder.vue'
import SalesItemsDetailGrid from './SalesItemsDetailGrid.vue'
import BaseGrid from '../base-ui/BaseGrid'
import { ExportService } from '../base-ui/export'

export default {
    name : 'salesOrder-grid',
    mixins:[BaseGrid],
    components:{
        SalesOrder,
        SalesItemsDetailGrid,
    },
    data: () => ({
        flex: null,
        tick : true,
        openDialog : false,
        selectedItem: null,
        selectedGrid: null,
    }),
    computed: {
        salesOrderModel: {
            get() {
                return this.selectedItem || this.newValue;
            },
            set(value) {
                if (this.selectedItem) {
                    this.selectedItem = value;
                } else {
                    this.newValue = value;
                }
            }
        }
    },
    created(){
        this.init()
        this.dataService = new DataService();
        this.exportService = new ExportService();
        // // initializes data maps
        // this._productMap = this._buildDataMap(this.dataService.getProducts());
        // this._countryMap = new DataMap(this.dataService.getCountries(), 'id', 'name');
        // this._colorMap = this._buildDataMap(this.dataService.getColors());
        // // initializes cell templates
        // this.historyCellTemplate = CellMaker.makeSparkline({
        //     markers: SparklineMarkers.High | SparklineMarkers.Low,
        //     maxPoints: 25,
        //     label: 'price history',
        // });
        // this.ratingCellTemplate = CellMaker.makeRating({
        //     range: [1, 5],
        //     label: 'rating'
        // });
    }, 
    beforeDestroy() {
        this.exportService.cancelExcelExport();
    },
    methods:{
        flexInitialized(flexGrid) {
            this.flex = flexGrid;
            this.$refs.flexGrid = flexGrid;
            let sd = new wjcCore.SortDescription("country", true);
            flexGrid.collectionView.sortDescriptions.push(sd);
            flexGrid.collectionView.currentChanged.addHandler(
                this._updateCurrentInfo.bind(this)
            );
            this._updateCurrentInfo();
        },
        onSelectionChanged(s) {
            let selectedItem = s.collectionView.currentItem;
            if (selectedItem) {
                this.selectedGrid = selectedItem.salesItems;
                this.selectedGrid = this.selectedGrid.map((user, index) => {
                    return { ...user, index: index };
                });

            } else {
                this.selectedGrid = [];
            }
            this.$nextTick(() => {
                if (this.$refs.flexGridDetails) {
                    this.$refs.flexGridDetails.refresh();
                }
            });
        },
        addNewRow() {
            this.selectedItem = null;
            this.openDialog = true;
        },
        editSelectedRow() {
            const flexGrid = this.$refs.flexGrid;
            const view = flexGrid.collectionView;

            if (view.currentItem) {
                this.selectedItem = null;
                this.selectedItem = JSON.parse(JSON.stringify(view.currentItem));
                this.edit(this.selectedItem);
                this.openDialog = true;
            }
        },
        async deleteSelectedRows() {
            try {
                if (!this.offline) {
                    const flexGrid = this.$refs.flexGrid;
                    const view = flexGrid.collectionView;
                    
                    if (view.currentItem) {
                        const deleteUrl = view.currentItem._links.salesOrder.href;
                        await axios.delete(axios.fixUrl(deleteUrl));
                        
                        view.remove(view.currentItem);
                        this.values = view.sourceCollection;
                    }
                }
            } catch(e) {
                this.snackbar.status = true
                if(e.response && e.response.data.message) {
                    this.snackbar.text = e.response.data.message
                } else {
                    this.snackbar.text = e
                }
            }
        },
        groupPanelInitialized: function (ctl) {
            this.groupPanel = ctl;
            if (this.flex) {
                this.groupPanel.grid = this.flex;
            }
        },
        exportToPdf: function() {
            this.exportService.exportToPdf(this.flex, {
                countryMap: this._countryMap,
                colorMap: this._colorMap,
                historyCellTemplate: this.historyCellTemplate
            });
        },
        _buildDataMap: function (items) {
            const map = [];
            for (let i = 0; i < items.length; i++) {
                map.push({ key: i, value: items[i] });
            }
            return new DataMap(map, 'key', 'value');
        },
        getFlex: function () {
            return this.flex;
        },
        departmentId(href){
            if(href){
                return href.split('/')[2]
            }
            return null;
        },
        getChangeCls: function (value) {
            if (wjCore.isNumber(value)) {
                if (value > 0) {
                    return 'change-up';
                }
                if (value < 0) {
                    return 'change-down';
                }
            }
            return '';
        },
    },
    filters: {
        safeCurrency: function (value) {
            if (wjCore.isNumber(value)) {
                return wjCore.Globalize.formatNumber(value, 'c');
            }
            if (!wjCore.isUndefined(value) && value !== null) {
                return wjCore.changeType(value, wjCore.DataType.String);
            }
            return '';
        }
    },
}
</script>

<style>
    @import "~@grapecity/wijmo.styles/wijmo.css";
    @import "~bootstrap/dist/css/bootstrap.css";
    @import url('https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.1.0/css/flag-icon.css');

    body {
        font-size:1.5em;
        font-family: "Noto Sans KR",-apple-system,BlinkMacSystemFont,"Segoe UI Light",Roboto,Oxygen-Sans,Ubuntu,Cantarell,"Helvetica Neue",sans-serif;
    }

    .toolbar-item {
        margin-bottom: 6px;
    }

    .wj-felx-grid .wj-flexgrid .wj-cell {
        padding: 3px;
        border:none;
    }

    .wj-cell.wj-state-invalid:not(.wj-header)::after {
        top: -14px;
        border: 14px solid transparent;
        border-right-color: red;
    }

    .flag-icon {
        box-shadow: 1px 1px 4px rgba(0,0,0,0.4);
    }

    .color-tile {
        display: inline-block;
        position: relative;
        width: 1em;
        height: 1em;
        border-radius: 50%;
        box-shadow: 1px 1px 4px rgba(0,0,0,0.4);
        vertical-align: middle;
    }

    .change-up {
        color: darkgreen;
    }
    .change-up:after {
        content: '\25b2';
    }
    .change-down {
        color: darkred;
    }
    .change-down:after {
        content: '\25bc';
    }

    .cell-rating {
        font-size: 12px;
    }

    .wj-flexgrid .wj-detail {
        padding: 4px 16px;
    }

    .wj-detail h3 {
        margin:10px 0;
    }
</style>
