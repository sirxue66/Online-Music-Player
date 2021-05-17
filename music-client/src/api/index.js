import Axios from 'axios';
import { get, post } from "./http";
/**
 * 调用接口
 */

// --------------------歌手Api
// 查询所有歌手接口
export const allSingerApi = () => get(`/singer/selectAllSingers`);
// 根据歌手名字模糊查询
export const searchSingerByNameApi = (name) => get(`/singer/selectByName?name=${name}`);
// 根据id查询歌手
export const searchSingerByIdApi = (id) => get(`/singer/selectById?id=${id}`);
// 根据歌手性别查询歌手列表
export const getSingerBySex = (sex) => get(`/singer/selectBySex?sex=${sex}`);

// -------------------歌手中歌曲Api
// 根据歌手ID查询歌曲
export const searchSongBySingerId =(id) =>get(`/songs/singer/song?singerId=${id}`)
// 根据歌曲id查询歌曲
export const searchSongByIdApi = (id) => get(`/songs/song?songId=${id}`);
// 查询所有歌曲
export const searchAllSongs = () => get(`/songs/allsongs`);
// 根据名字模糊查询歌曲列表
export const searchSongLikeName = (name) => get(`/songs/searchSong?name=${name}`)

// ------------------歌单Api
// 查询所有歌单
export const allSongListApi =() =>get(`/songList/allSongList`);
// 根据风格模糊查询歌单列表
export const getSongListByStyle = (style) => get(`/songList/selectSongListByStyle?style=${style}`)

// ------------------歌单中歌曲Api
//根据歌单id查询歌曲列表
export const listSongDetailApi = (songListId) => get(`/listsong/detail?songListId=${songListId}`);

// ---------------------用户管理Api
// 获取所有用户列表
export const allConsumersApi = () => get(`/consumer/allConsumer`);
// 用户注册
export const registeredApi = (params) => post(`/consumer/add`,params);
// 用户登录
export const logInApi = (params) => post(`/consumer/login`,params);
// 根据id查询用户信息
export const getConsumerById = (id) => get(`/consumer/selectConsumerById?id=${id}`);
// 更新用户信息
export const updateUserMsg = (params) => post(`/consumer/update`,params);

// 下载歌曲
export const downLoad = (url) => Axios({
  method: 'get',
  url: url,
  responseType: 'blob'
})

// -----------------------评分
// 提交评分
export const addRankApi = (params) => post(`/rank/add`,params);
// 获取评分
export const getAverageRank = (songListId) => get(`/rank?songListId=${songListId}`);

//---------------------评论
// 增加评论
export const addCommentApi = (params) => post(`/comment/add`,params);
// 修改评论
export const updateComment = (params) => post(`/comment/update`,params);
//删除评论
export const deleteComment = (id) =>get(`/comment/delete?id=${id}`);
// 查询一个歌单或歌曲下的评论列表
export const getAllComments = (type,id) => {
  if(type === 0){
    return get(`/comment/commentsOfSong?songId=${id}`)
  } else {
    return get(`/comment/commentsOfSongList?songListId=${id}`)
  }
}
// 给某条评论点赞
export const likeComment = (params) => post(`/comment/beautiful`,params);

//----------------------------收藏
//增加收藏
export const addCollect = (params) => post(`/collect/add`,params);
//根据用户id和歌曲id删除收藏
export const deleteCollectById = (userId,songId) => get(`/collect/deleteById?userId=${userId}&songId=${songId}`);
// 查询用户的收藏歌曲或歌单
export const getCollectByUserId = (userId) => get(`/collect/collectOfUserId?userId=${userId}`);
