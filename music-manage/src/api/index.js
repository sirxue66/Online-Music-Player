import { get, post } from "./http"
/**
 * 调用接口
 */

//  判断管理员是否登录成功
export const getLoginStatus = (params) => post(`/admin/login/status`,params);

// --------------------歌手Api
// 查询所有歌手接口
export const allSingerApi = () => get(`/singer/selectAllSingers`);
// 根据歌手名字模糊查询
export const searchSingerByNameApi = (params) => get(`/singer/selectByName`,params);
// 根据id查询歌手
export const searchSingerByIdApi = (id) => get(`/singer/selectById?id=${id}`);
// 添加歌手接口
export const addSingerApi = (params) => post(`/singer/insert`,params);
// 修改歌手接口
export const editSingerApi = (params) => post(`/singer/update`,params);
// 删除单个歌手
export const deleteSingerApi = (id) => get(`/singer/delete?id=${id}`);

// -------------------歌手中歌曲Api
// 根据歌手ID查询歌曲
export const searchSongBySingerId =(id) =>get(`/songs/singer/song?singerId=${id}`)
// 修改歌曲信息
export const updateSongApi = (params) => post(`/songs/update`,params);
// 删除歌曲
export const deleteSongApi = (id) => get(`/songs/delete?id=${id}`);
// 根据歌曲id查询歌曲
export const searchSongByIdApi = (id) => get(`/songs/song?songId=${id}`);
// 查询所以歌曲
export const searchAllSongs = () =>get(`/songs/allsongs`);

// ------------------歌单Api
// 查询所有歌单
export const allSongListApi =() =>get(`/songList/allSongList`);
// 添加歌单接口
export const addSongListApi = (params) => post(`/songList/add`,params);
// 修改歌单接口
export const editSongListApi = (params) => post(`/songList/update`,params);
// 删除单个歌单
export const deleteSongListApi = (id) => get(`/songList/delete?id=${id}`);
// 根据歌单id查询歌单信息
export const searchSongListById = (id) => get(`/songList/selectSongListById?id=${id}`);


// ------------------歌单中歌曲Api
//根据歌单id查询歌曲列表
export const listSongDetailApi = (songListId) => get(`listsong/detail?songListId=${songListId}`);
//给歌单增加歌曲
export const addListSong = (params) => post(`listsong/add`,params);
//删除歌单的歌曲
export const deleteListSong = (songId,songListId) => get(`listsong/delete?songId=${songId}&songListId=${songListId}`);

// ---------------------用户管理Api
// 获取所有用户列表
export const allConsumersApi = () => get(`/consumer/allConsumer`);
// 增加用户
export const addConsumerApi =(params) => post(`/consumer/add`,params);
// 修改用户信息
export const editConsumerApi = (params) => post(`/consumer/update`,params);
// 删除单个用户
export const deleteConsumerApi = (id) => get(`/consumer/delete?id=${id}`);
// 根据id查询用户
export const getConsumerById = (id) =>get(`/consumer/selectConsumerById?id=${id}`);

// -------------------评论
// 获取所有评论
export const allCommentApi = () => get(`/comment/allComments`);
// 删除一条评论
export const deleteCommentApi = (id) => get(`/comment/delete?id=${id}`);
