<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="mini" @click="deleteAll">批量删除</el-button>
                <el-input v-model="searchWords" size="mini" placeholder="筛选关键词" class="handle-input"></el-input>
            </div>
        </div>
        <el-table size="mini" ref="multipleTable" border style="width:100%" height="740px" :data="data" @selection-change="deleteSelectOptions">
            <el-table-column type="selection" width="40"></el-table-column>
            <el-table-column prop="userId" label="用户名" align="center" width="150px"></el-table-column>
            <el-table-column prop="songId" label="歌曲名" align="center" width="200px"></el-table-column>
            <el-table-column prop="songListId" label="歌单名" align="center" width="250px"></el-table-column>
            <el-table-column prop="content" label="评论内容" align="center"></el-table-column>
            <el-table-column label="操作" width="150" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="deleteComment(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页footer -->
    <div class="pageFooter">
      <el-pagination background :current-page="nowPage" :page-size="pageSize"
      layout="total,prev, pager, next" :total="tableData.length" @current-change="handlePage"></el-pagination>
    </div>
    </div>
</template>

<script>
import { tool } from '../tools/index';
import {allCommentApi,deleteCommentApi,getConsumerById,searchSongByIdApi,searchSongListById} from '../api/index';

export default {
    mixins: [tool],
    data(){
        return{
            tableData: [],
            tableData1: [],
            searchWords: '',
            selectoptions: '',     //删除选择项
            // 分页
            pageSize: 17,
            nowPage: 1,
        }
    },
    computed: {
    // 一次性拿到列表数据，在前端计算实现分页功能,使用computed 中的data方法，view中直接绑定data 就可以了
    data() {
      return this.tableData.slice(
        (this.nowPage - 1) * this.pageSize,
        this.nowPage * this.pageSize
      );
    }
  },
    watch:{
        //搜索框里面的内容发生变化的时候，搜索结果table列表的内容跟着它的内容发生变化
        searchWords: function(){
            if(this.searchWords == ''){
                this.tableData = this.tableData1;
            }else{
                this.tableData = [];
                for(let item of this.tableData1){
                    if(item.content.includes(this.searchWords)){
                        this.tableData.push(item);
                    }
                }
            }
        }
    },
    created(){
        this.getData();
    },
    methods:{
        //获取所有评论列表
        getData(){
            this.tableData1 = [];
            this.tableData = [];
            allCommentApi().then(response => {
              response.forEach(item => {
                // 将userId换成用户名
                getConsumerById(item.userId).then(res => {
                  // console.log(res.username);
                  item.userId = res.username;
                });
                // 歌曲名
                if(!item.songId){
                  item.songId = "*"
                }else {
                  searchSongByIdApi(item.songId).then(res => {
                    item.songId = res.name;
                  });
                }
                // 歌单名
                if(!item.songListId){
                  item.songListId = "*"
                }else {
                  searchSongListById(item.songListId).then(res => {
                    item.songListId = res.title;
                  });
                }
              })
              this.tableData = response;
              this.tableData1 = response;
            })
        },
        handlePage(currentPage){
          this.nowPage = currentPage;
        },
        //删除一条评论
        deleteComment(id){
          this.$confirm('确定删除该评论吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteOne(id);
         }).catch(() => {});
        },
      // 删除方法
      deleteOne(id){
        deleteCommentApi(id).then(response => {
          if(response.code === 1){
            this.getData();
            this.$message.success(response.infoMsg);
          } else {
            this.$message.error(response.infoMsg);
          }
        })
      }

    }
}
</script>
<style scoped>
    .handle-box{
        margin-bottom: 15px;
    }
    .handle-input{
        width: 300px;
        display: inline-block;
    }
    .pageFooter {
  /* 灵活布局 */
  display: flex;
  /* 内容居中 */
  justify-content: center;
}
</style>
