//标签输入组件
var tagInput = {
	isLogger: false,
	
	valueId:'#tagids',
	tagBoxSelect:'#tag-input',
	tagids: [],
	oldValue: undefined,
	
	/**
	 * 由业务程序调用本方法初始化标签输入组件
	 */
	init: function(tags, boxSelector, idSelector){
		tagInput.destroy();
		//初始化数据
		if(boxSelector)tagBoxSelect = boxSelector;
		if(idSelector)valueId = idSelector;
		
		//渲染模板
		var template = tagInput.renderInput((idSelector || tagInput.valueId));
		if(tagInput.isLogger)console.log('tag input init: ', tags, template, tagInput.tagBoxSelect);
		//增加元素
		boxSelector ? template.appendTo(boxSelector) : template.appendTo(tagInput.tagBoxSelect);
		if(tags && tags.length>0){
			$.each(tags, function(i, t){
				tagInput.select(t.tagid, t.tagname);
			});
		}
	},
	destroy: function(){
		$(tagInput.tagBoxSelect).unbind().children().remove();
		tagInput.valueId='#tagids';
		tagInput.tagBoxSelect='#tag-input';
		tagInput.tagids= [];
		tagInput.oldValue= undefined;
	},
	renderInput: function(valueSelector){
		if(tagInput.isLogger)console.log('try render tag input', valueSelector);
		var id = valueSelector.replace('#', '').replace('.', '');
		var placeholder = "请输入标签";
		var _template ="<div class='formContent'>"+
							"<input type='hidden' id='"+id+"' name='"+id+"' />"+
							"<input type='text' name='tagnameinput' maxlength='30' class='inputLabel' />"+
							"<em name=\"tagnameinput\" class=\"placeholder\">"+placeholder+"</em>"+
							"<button class='MsureLabel' type='button'>确定</button>"+
							"<div class='form-pulldown hidden'></div>"+
							"<div class='errorTip'><p class='triangle'></p><div>至少输入一个标签</div></div>"+
							"</div>"+
						"<div class='addedLabel'></div>";
		return tagInput.bindEvent($(_template));
	},
	/**
	 * 对静态模板绑定事件
	 * @param template
	 * @returns
	 */
	bindEvent: function(template){
		template.find("input[name='tagnameinput']").keyup(function(e){
			if(!(e.keyCode == 13 || e.keyCode == 38 || e.keyCode == 40)){
				e.stopPropagation();tagInput.loadTips($(this).val().trim());return false;
			}
		}).keydown(function(e){
			switch (e.keyCode){
			case 13: e.stopPropagation();tagInput.makeTag();return false;
			case 38: e.stopPropagation();tagInput.selectUp();return false;
			case 40: e.stopPropagation();tagInput.selectDown();return false;
			default: return;
			}
		});
		template.find('.MsureLabel').click(tagInput.makeTag);
		return template;
	},
	loadTips: function(currentValue){
		if(tagInput.isLogger)console.log('input new=', currentValue, 'old=', tagInput.oldValue);
		if(currentValue && currentValue != tagInput.oldValue){
			tagInput.oldValue = currentValue;
			$.post("/tag/find-input-tips.json", {'tagname': currentValue, 'pageSize':5}, function(data) {
				if('000000' == data.result.retCode){
					tagInput.addSelections(data.result.data.tags);
				}
			},"json");
		}
	},
	/**
	 * 标签提示
	 * @param tag
	 */
	addSelections: function(tags){
		if(tags && tags.length>0){
			var box = $(tagInput.tagBoxSelect).find('.form-pulldown').fadeIn().empty();
			$.each(tags, function(i, tag){
				var _tag = $("<p tagid='" + tag.tagid + "'>" + tag.tagname + "</p>");
				_tag.click(function(){
					var obj = $(this);
					tagInput.select(obj.attr('tagid'), obj.text());
					tagInput.clearInput();
				});
				box.append(_tag);
			});
		}else{
			$(tagInput.tagBoxSelect).find('.form-pulldown').fadeOut().empty();
		}
	},
	/**
	 * 选择标签
	 * @param tagid
	 * @param tagname
	 */
	select: function(tagid, tagname){
		if(tagInput.tagids.length >= 5){
			alert("最多支持5个标签！");
			return;
		}
		//已选择的标签去重
		if($.inArray(tagid, tagInput.tagids)<0){
			if(tagInput.isLogger)console.log('tag select ', tagid, tagname);
			tagInput.tagids.push(tagid);
			var box = $(tagInput.tagBoxSelect);
			box.find(tagInput.valueId).val(tagInput.tagids.join());
			var selected = $("<a databind='"+tagid+"'>"+tagname+"<span></span></a>");
			selected.find('span').click(tagInput.remove);
			box.find('.addedLabel').append(selected);
		}else{
			if(tagInput.isLogger)console.log('tag select but exist. ignore', tagid, tagname);
		}
	},
	/**
	 * 移除标签
	 */
	remove: function(){
		var obj = $(this).parent();
		var tagid = obj.attr('databind');
		obj.remove();
		$.each(tagInput.tagids, function(i, id){
			if(tagid == id){
				tagInput.tagids.splice(i, 1);
				return false;
			}
		});
		if(tagInput.isLogger)console.log('current tagids ', tagInput.tagids.join(),' removing ', tagid);
		$(tagInput.tagBoxSelect).find(tagInput.valueId).val(tagInput.tagids.join());
	},
	
	makeTag: function(){
		var box = $(tagInput.tagBoxSelect);
		var tagnameInput = box.find("input[name='tagnameinput']");
		var tagVal = tagnameInput.val().trim();
		if(tagVal.length==0)return;
		$.post('/tag/add.json', {tagname: tagVal}, function(data){
			if('000000' == data.result.retCode){
				var tag = data.result.data.tag;
				tagInput.select(tag.tagid, tag.tagname);
			}
		},"json");
		tagInput.clearInput();
	},
	selectUp: function(){
		var box = $(tagInput.tagBoxSelect);
		var tips = box.find('.form-pulldown');
		if(tagInput.isLogger)console.log('select up, ', tips.is(':visible'));
		if(tips.is(":visible")){
			var active = tips.children('p.active');
			if(active.length>0){
				if(active.prev('p').length > 0){
					tagInput.activeTag(active.prev('p'));
				}
			}
		}
	},
	selectDown: function(){
		var box = $(tagInput.tagBoxSelect);
		var tips = box.find('.form-pulldown');
		if(tagInput.isLogger)console.log('select down, ', tips.is(':visible'));
		if(tips.is(":visible")){
			var active = tips.children('p.active');
			if(active.length>0){
				if(active.next('p').length > 0){
					tagInput.activeTag(active.next('p'));
				}
			}else{
				tagInput.activeTag(tips.children('p:first'));
			}
		}
	},
	clearInput: function(){
		var box = $(tagInput.tagBoxSelect);
		box.find("input[name='tagnameinput']").val(''); //清空输入内容
		box.find('.form-pulldown').fadeOut();
		box.find("div:first").removeClass("error");
		tagInput.oldValue = undefined;
	},
	activeTag: function(p){
		p.addClass('active').siblings().removeClass('active');
		$(tagInput.tagBoxSelect).find("input[name='tagnameinput']").val(p.text());
	}
};