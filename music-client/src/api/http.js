import axios from 'axios'
axios.defaults.timeout = 5000;
// 允许跨域
axios.defaults.withCredentials = true;
// 设置请求头，与后端一致
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencode;charset=UTF-8';

// 设置url
axios.defaults.baseURL = 'http://localhost:8888';

// 响应拦截器
axios.interceptors.response.use(
  // 连接到接口，也就是接口访问成功时
  response => {
    // status为200，接口访问成功
    if(response.status === 200){
      return Promise.resolve(response);
    } else {
      return Promise.reject(response);
    }
  },
  // 返回错误页面时的处理
  error => {
    if(error.response.status){
      switch(error.response.status){
        // 未登录
        case 401:
          router.replace({
            path: "/",
            query: {
              redirect: router.currentRouter.fullPath   //未登录返回登录页面，并保存当前地址
            }
          });
          break;
        // 未找到页面
        case 404:
          break;
      }
      return Promise.reject(error.response);
    }
  },

);

/**
 * 封装 get 方法
 */
export function get(url,pramas={}){
  return new Promise((resolve,reject) => {
    axios.get(url,{pramas: pramas})
    .then(response => {
      resolve(response.data);
    })
    .catch(err => {
      reject(err);
    })
  })
}

/**
 * 封装 post 方法
 */
export function post(url,data={}){
  return new Promise((resolve,reject) => {
    axios.post(url,data)
    .then(response => {
      resolve(response.data);
    })
    .catch(err => {
      reject(err);
    })
  })
}
