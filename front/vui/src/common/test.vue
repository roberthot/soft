<template>
    <el-table :data="menus" border>
        <el-table-tree-column :expandAll="expandAll"
            file-icon="icon icon-file" 
            folder-icon="icon icon-folder" 
            prop="label" label="labelname" width="220"></el-table-tree-column>
        <el-table-column prop="description" label="description"  width="180" :expandAll="expandAll"></el-table-column>
    </el-table>
</template>
<script>
export default {
    data(){
        return {
            expandAll:true,
            menus:[]
        }
    }
}
</script>
