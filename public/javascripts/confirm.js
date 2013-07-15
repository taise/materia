'use strict'

$(function(){
  $('form').submit(function() {
    return confirm("入力内容を登録しますか？");
  })
});
