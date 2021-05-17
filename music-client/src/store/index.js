import Vue from 'vue'
import Vuex from 'vuex'
import User from './user'
import Song from './song'
import Config from './config'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    Config,
    User,
    Song
  }
})

export default store
