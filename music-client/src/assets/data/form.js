const validateName = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("用户名不能为空"));
  }
  if (value.length < 3 || value.length > 10) {
    return callback(new Error("请输入3-10个字符，推荐使用中文"));
  }
  else {
    return callback();
  }
};
const validatePassword = (rule, value, callback) => {
  var changeReg = /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)])+$).{6,20}$/;
  if (!value) {
    return callback(new Error("请输入密码"));
  }
  if (value.length < 6 || value.length > 15) {
    return callback(new Error("请输入6-15个字符"));
  }
  if (!changeReg.test(value)) {
    return callback(new Error("密码只能由字母、数字或标点组成，且至少包括其中两种"));
  } else {
    return callback();
  }
};
// 匹配规则
const rules = {
  username: [
    { required: true, validator: validateName, trigger: 'blur' }
  ],
  password: [
    { required: true, validator: validatePassword, trigger: 'blur' }
  ],
  sex: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phoneNum: [
    { required: true, message: '请输入手机号', trigger: 'blur' }
  ],
  email: [
    { message: '请输入邮箱地址', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
  ],
  birth: [
    { required: true, type: 'date', message: '请选择日期', trigger: 'change' }
  ],
  introduction: [
    { required: true, message: '请输入介绍', trigger: 'blur' }
  ],
  location: [
    { required: true, message: '请选择地区', trigger: 'change' }
  ]
}

// 地区选择
const cities = [{
  value: '北京',
  label: '北京'
}, {
  value: '天津',
  label: '天津'
}, {
  value: '河北',
  label: '河北'
}, {
  value: '山西',
  label: '山西'
}, {
  value: '内蒙古',
  label: '内蒙古'
}, {
  value: '辽宁',
  label: '辽宁'
}, {
  value: '吉林',
  label: '吉林'
}, {
  value: '黑龙江',
  label: '黑龙江'
}, {
  value: '上海',
  label: '上海'
}, {
  value: '江苏',
  label: '江苏'
}, {
  value: '浙江',
  label: '浙江'
}, {
  value: '安徽',
  label: '安徽'
}, {
  value: '福建',
  label: '福建'
}, {
  value: '江西',
  label: '江西'
}, {
  value: '山东',
  label: '山东'
}, {
  value: '河南',
  label: '河南'
}, {
  value: '湖北',
  label: '湖北'
}, {
  value: '湖南',
  label: '湖南'
}, {
  value: '广东',
  label: '广东'
}, {
  value: '广西',
  label: '广西'
}, {
  value: '海南',
  label: '海南'
}, {
  value: '重庆',
  label: '重庆'
}, {
  value: '四川',
  label: '四川'
}, {
  value: '贵州',
  label: '贵州'
}, {
  value: '云南',
  label: '云南'
}, {
  value: '西藏',
  label: '西藏'
}, {
  value: '陕西',
  label: '陕西'
}, {
  value: '甘肃',
  label: '甘肃'
}, {
  value: '青海',
  label: '青海'
}, {
  value: '宁夏',
  label: '宁夏'
}, {
  value: '新疆',
  label: '新疆'
}, {
  value: '香港',
  label: '香港'
}, {
  value: '澳门',
  label: '澳门'
}, {
  value: '台湾',
  label: '台湾'
}]

export {
  rules,
  cities
}
