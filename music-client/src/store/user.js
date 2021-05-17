const song = {
  state:{
      userID: '',     //用户ID
      username: '',                //用户名
      avator: '',              //头像
      sex: '',                  //用户性别
      phoneNum: '',                  //手机号
      email: '',                 //用户邮箱
      birth: '',             //生日
      introduction: '',                  //个人签名
      location: '',               //地区
  },
  getters: {
      userID: state => {
          let userID = state.userID;
          if(!userID.length){
              userID = JSON.parse(window.sessionStorage.getItem('userID')||null);
          }
          return userID;
      },
      username: state => {
          let username = state.username;
          if(!username){
            username = JSON.parse(window.sessionStorage.getItem('username')||null);
          }
          return username;
      },
      avator: state => {
          let avator = state.avator;
          if(!avator){
              avator = JSON.parse(window.sessionStorage.getItem('avator')||null);
          }
          return avator;
      },

  },
  mutations: {
      setUserId: (state,userID) => {
          state.userID = userID;
          window.sessionStorage.setItem('userID',JSON.stringify(userID));
      },
      setUserName: (state,username) => {
          state.username = username;
          window.sessionStorage.setItem('username',JSON.stringify(username));
      },
      setAvator: (state,avator) => {
          state.avator = avator;
          window.sessionStorage.setItem('avator',JSON.stringify(avator));
      },
    }
}

export default song
