export const tool = {
  methods: {
    // 获取后端图片地址
    getImageUrl(url){
      return url ? this.$store.state.Config.HOST+url : '../assets/img/user.jpg'
    },
    // 将歌曲名字拆分，分为歌曲名字和歌手名字
    SingerName(str){
      let name = str.split('-');
      return name[0];
    },
    SongName(str){
      let name = str.split('-');
      return name[1];
    },
    //转换生日时间
    changeBirth(birth){
      return String(birth).substr(0,10);
    } ,
    // 转换性别
    changeSex(num){
      if(num === 1){
        return "男";
      }else if(num === 0){
        return "女";
      } else {
        return "组合";
      }
    },
    //解析歌词
    parseLyric(text){
      let lines = text.split("\n");                   //将歌词按行分解成数组
      let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g; //时间格式的正则表达式
      let result = [];                                //返回值
      //对于歌词格式不对的直接返回
      if(!(/\[.+\]/.test(text))){
          return [[0,text]]
      }
      //去掉前面格式不正确的行
      while(!pattern.test(lines[0])){
          lines = lines.slice(1);
      }
      //遍历每一行，形成一个每行带着俩元素的数组，第一个元素是以秒为计算单位的时间，第二个元素是歌词
      for(let item of lines){
          let time = item.match(pattern);  //存前面的时间段
          let value = item.replace(pattern,'');//存后面的歌词
          for(let item1 of time){
              let t = item1.slice(1,-1).split(":");   //取出时间，换算成数组
              if(value!=''){
                // 将分钟和秒都转换成秒，加起来
                  result.push([parseInt(t[0],10)*60 + parseFloat(t[1]),value]);
              }
          }
      }
      //按照第一个元素--时间--排序
      result.sort(function(a,b){
          return a[0] - b[0];
      });
      return result;
  },
  }
}
