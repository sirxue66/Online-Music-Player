export const tool = {
  methods: {
    // 根据相对地址获取绝对地址
    getUrl(url) {
      return `${this.$store.state.HOST}/${url}`;
    },
    // 转换性别
    changeSex(sex) {
      if (sex == 0) {
        return "女";
      } else if (sex == 1) {
        return "男";
      } else {
        return "组合";
      }
    },
    // 转换生日格式
    changeBirth(birth) {
      return String(birth).substr(0, 10);
    },
    // 上传图片之前的校验
    beforeUpload(file){
      const isJPG = (file.type === 'image/jpeg') || (file.type === 'image/png');
      if(!isJPG){
        this.$message.error("图片只支持jpeg和png格式");
        return false;
      }
      const is2M = (file.size /1024 /1024 < 2);
      if(!is2M){
        this.$message.error("图片大小不能超过2MB");
        return false;
      }
      return true;
    },
    // 上传成功
    uploadSuccess(res){
      // 让_this = 使用页面的this，就可以调用使用页面的函数，相当于把要使用页面的函数复制到本页面中使用一样
      let _this = this;
      if(res.code == 1){
        _this.allData();
        this.$message.success("上传成功");
      } else {
        this.$message.error("上传失败");
      }
    },
    /**
     * 复用时，要注意事件@selection-change = "deleteSelectOptions"  已选项selectoptions   和单项删除方法deleteOne 要固定，不能改变
     *
     * */
    // 多选删除项
    deleteSelectOptions(select){
      let _this = this;
      _this.selectoptions = [];
      _this.selectoptions = select;
    },
    // 执行多项删除操作
    deleteAll(){
      let _this = this;
      this.selectoptions.forEach(item => {
        _this.deleteOne(item.id);
      })
      _this.selectoptions = [];
    },
      // 解析歌词
      handleLyric(content){
        // 以空格分隔
        let lines = content.split("\n");
        let lyricResult = [];
        for(let item of lines){
          lyricResult.push(item);
        }
        return lyricResult;
      }


  }
}



