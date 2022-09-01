(function($,window,document){function Paging(el,options){this.el=el;this.options={pageNo:options.initPageNo||1,totalPages:options.totalPages||1,totalCount:options.totalCount||'',slideSpeed:options.slideSpeed||0,jump:options.jump||false,callback:options.callback||function(){}};this.init();}
Paging.prototype={constructor:Paging,init:function(){this.createDom();this.bindEvents();},createDom:function(){console.info(this);var that=this,ulDom='',jumpDom='',content='',liWidth=45,totalPages=that.options.totalPages,wrapLength=0;totalPages>5?wrapLength=5*liWidth+8:wrapLength=totalPages*liWidth+8;for(var i=1;i<=that.options.totalPages;i++){i!=1?ulDom+='<li>'+i+'</li>':ulDom+='<li class="sel-page">'+i+'</li>';}
that.options.jump?jumpDom='<input type="text" placeholder="1" class="jump-text jumpText" ><button type="button" class="jump-button" >跳转</button>':jumpDom='';content='<div class="pagger-box"><button type="button"  class="turnPage first-page">首页</button>'+
'<button class="turnPage pre-page">上一页</button>'+
'<div class="pageWrap" style="width:'+wrapLength+'px">'+
'<ul class="pageSelect" style="transition:all '+that.options.slideSpeed+'ms">'+
ulDom+
'</ul></div>'+
'<button class="turnPage next-page">下一页</button>'+
'<button type="button" class="last-page ">尾页</button>'+
jumpDom+
'<p class="total-pages">共&nbsp;'+
that.options.totalPages+
'&nbsp;页</p>'+
'<p class="total-count">'+
that.options.totalCount+
'</p></div>';that.el.html(content);},bindEvents:function(){var that=this;var $el=that.el;var pageSelect=$el.find('.pageSelect'),lis=pageSelect.children(),liWidth=45,totalPages=that.options.totalPages,pageIndex=that.options.pageNo,distance=0,prePage=$el.find('.pre-page'),nextPage=$el.find('.next-page'),firstPage=$el.find('.first-page'),lastPage=$el.find('.last-page'),jumpBtn=$el.find('.jump-button'),jumpText=$el.find('.jump-text');prePage.on('click',function(){pageIndex--;if(pageIndex<1)pageIndex=1;handles(pageIndex);})
jumpText.on("keyup",function(event){if(event.keyCode==13){jumpBtn.trigger("click");}});nextPage.on('click',function(){pageIndex++;if(pageIndex>lis.length)pageIndex=lis.length;handles(pageIndex);})
firstPage.on('click',function(){pageIndex=1;handles(pageIndex);})
lastPage.on('click',function(){pageIndex=totalPages;handles(pageIndex);})
jumpBtn.on('click',function(){var jumpNum=parseInt(jumpText.val().replace(/\D/g,''));if(jumpNum&&jumpNum>=1&&jumpNum<=totalPages){pageIndex=jumpNum;handles(pageIndex);jumpText.val(jumpNum);}else{jumpText.val(totalPages);jumpBtn.trigger("click");}})
lis.on('click',function(){pageIndex=$(this).index()+1;handles(pageIndex);})
function handles(pageIndex){jumpText.val(pageIndex);lis.removeClass('sel-page').eq(pageIndex-1).addClass('sel-page');if(totalPages<=5){that.options.callback(pageIndex);return false;}
if(pageIndex>=3&&pageIndex<=totalPages-2)distance=(pageIndex-3)*liWidth;if(pageIndex==2||pageIndex==1)distance=0;if(pageIndex>totalPages-2)distance=(totalPages-5)*liWidth;pageSelect.css('transform','translateX('+(-distance)+'px)');pageIndex==1?firstPage.attr('disabled',true):firstPage.attr('disabled',false);pageIndex==1?prePage.attr('disabled',true):prePage.attr('disabled',false);pageIndex==totalPages?lastPage.attr('disabled',true):lastPage.attr('disabled',false);pageIndex==totalPages?nextPage.attr('disabled',true):nextPage.attr('disabled',false);that.options.callback(pageIndex);}
handles(that.options.pageNo);}}
$.fn.paging=function(options){return new Paging($(this),options);}})(jQuery,window,document);