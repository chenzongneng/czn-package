// 注册单选
Vue.component('single-select', {
	props: ['optionsdata','selecteddata'],
	data: function() {
		var data = {
            originOptions: [],
            displayOptions: [],
            show: false,
            search: '',
            selected: {
                id: "",
                name: ""
            }
		}
		return data
	},
	computed:{
		// selectedFocus:function(){
		// 	return {
		// 		"single-selected-focus": this.show
		// 	} 
		// },
		// dropUp: function(){
		// 	return {
		// 		"drop-up": this.show
		// 	}
		// }
	},
	ready: function(){
		window.addEventListener('click',this.blur)
		//console.log(JSON.stringify(this.data))
	},
    watch: {
        optionsdata: function (val, oldVal) {
            // console.log('option old: ' + JSON.stringify(oldVal))
            // console.log('option new: ' + JSON.stringify(val))
            this.originOptions = val;
            this.show = false;
            // 默认值
            if (this.selected.id == ''){
                this.selected = this.originOptions[0];
            }

        },
        selecteddata: function(val, oldVal){
            // console.log('selected old: ' + JSON.stringify(oldVal))
            // console.log('selectednew: ' + JSON.stringify(val))
            this.selected = val
        }

    },
	// events: {
	// 	// ajax获取originOptions后 初始化
	// 	initSingle: function(originOptions,selected){
 //            // console.log(JSON.stringify(originOptions))
 //            // console.log(JSON.stringify(selected))
	// 		this.originOptions = originOptions;
 //            // 设置默认值为originlist的第一个
 //            if (selected){
 //                this.selected = selected

 //            }
 //            else{
 //                this.selected = originOptions[0]
 //            }

 //            //空判断
 //            if (this.selected){
 //                this.singleSelect(this.selected.id);
 //            }
	// 	}
	// },
	methods:{
        singleFocus: function(){
            if (!this.show){
                document.body.click();
                console.log('single show')
                this.show = true;
                this.singleSearch();
                this.searchInputFocus();
            }
            else{
                this.blur();
            }
        },
        searchInputFocus: function(){
            var searchInput = this.$el.getElementsByClassName('search-input')[0];

            this.$nextTick(function(){
                searchInput.focus();
            })

        },
        singleSelect: function(id){
            var mySelf = this;
            // var originOptions = mySelf.single.originOptions;
            var displayOptions = mySelf.displayOptions;
            for (var i=0; i<displayOptions.length;i++){
                var item = displayOptions[i]
                //在展示数组里找 找到后1.添加到selected
                if (item.id == id){
                    var selected = mySelf.selected;
                    selected.id = item.id
                    selected.name = item.name
                }
            }
            mySelf.show = false;
            this.search = '';
            //传递给父级组件
            // console.log('派发！！')
            this.$dispatch('selected', this.selected)
            // console.log('选中的是' + JSON.stringify(this.selected))
        },
        singleSearch: function(){
            var mySelf = this;
            var search = mySelf.search;
            var REG_RULE = new RegExp(search,"i") //根据用户输入值做正则
            var originOptions = mySelf.originOptions;
            //将展示列表置空 然后用正则去原始列表中匹配
            mySelf.displayOptions = [];

            //console.log('搜索的内容是' + search)
            for (var i=0;i<originOptions.length;i++){
                var item = originOptions[i]
                // console.log('当前校验的是' + item.name)
                // console.log('校验的结果是' + REG_RULE.test(item.name))
                if (REG_RULE.test(item.name)){
                    mySelf.displayOptions.push(item)
                }
            }
        },
        blur: function(){
            console.log('hide single！！')
            this.show = false;
            this.search = '';
        }
	},
	template: 
		'<div class="functional-select-wrapper" v-on:click.stop="singleFocus()">' +
			'<label class="display-container clearfix" v-bind:class="(show)?\'single-selected-focus\':\'\'">' + 
                '<p v-show="selected.id == \'\' ">' +
                    '<span v-if="originOptions.length != 0 ">请选择</span>' +
                    '<span v-else>没有选项</span>' +
                '</p>' +
                '<p class="single-selected">{{ selected.name }}</p>' + 
				'<i class="drop" v-bind:class="(show)?\'drop-up\':\'\'">▼</i>' +
			'</label>' +
            '<div class="options-container" v-show="show">' +
                '<div class="search-container">' +
                    '<input placeholder="search here" class="search-input" v-model="search" v-on:keyup="singleSearch()" v-on:click.stop />' +
                '</div>' +
                '<ul class="options-ul-list">' +
                    '<li v-show="displayOptions.length == 0">没有查询到数据</li>' +
                    '<li v-for="item in displayOptions" v-on:click.stop.prevent="singleSelect(item.id)" v-bind:class=" (item.id == selected.id)?\'selected\':\'\' ">{{ item.name }}</li>' +
                '</ul>' +
            '</div>' +
        '</div>'

})

// 注册多选
Vue.component('multiple-select', {
	props: ['optionsdata','selecteddata'],
	data: function() {
		var data = {
            originOptions: [],
            displayOptions: [],
            show: false,
            search: '',
            selectedList: [],
            selectedIdList: []
		}
		return data
	},
	computed:{
		// selectedFocus:function(){
		// 	return {
		// 		"single-selected-focus": this.show
		// 	} 
		// },
		// dropUp: function(){
		// 	return {
		// 		"drop-up": this.show
		// 	}
		// }
	},
	ready: function(){
		window.addEventListener('click',this.blur)
	},
    watch: {
        optionsdata: function (val, oldVal) {
            // console.log('option old: ' + JSON.stringify(oldVal))
            // console.log('option new: ' + JSON.stringify(val))
            this.show = false;
            this.originOptions = val;
        },
        selecteddata: function(val, oldVal){
            // console.log('selected old: ' + JSON.stringify(oldVal))
            // console.log('selected new: ' + JSON.stringify(val))
            this.selectedList = val;
            this.selectedIdList = [];
            // 赋值selectedList
            for (var i=0;i<this.selectedList.length;i++){
                var item = this.selectedList[i];
                this.selectedIdList.push(item.id); 
            }
        }

    },
	// events: {
	// 	// ajax获取originOptions后 初始化
	// 	initMultiple: function(originOptions,selectedList){
 //            this.originOptions = originOptions;
 //            this.selectedList = [];
 //            this.selectedIdList = [];
 //            this.show = false;
 //            this.multipleInitSearch();
            
 //            if(selectedList){
 //                // this.selectedList = selectedList
 //                for (var i=0;i<selectedList.length;i++){
 //                    var item = selectedList[i];
 //                    this.multipleSelect(item.id);
 //                }
 //            }
	// 	}

	// },
	methods:{
        multipleFocus: function(){
            if (!this.show){
                document.body.click();
                console.log('multiple show');
                this.show = true;
                this.multipleSearch();
                this.searchInputFocus();
            }
            else{
                this.blur();
            }
        },
        searchInputFocus: function(){
            var searchInput = this.$el.getElementsByClassName('search-input')[0];

            this.$nextTick(function(){
                searchInput.focus();
            })

        },
        multipleSelect: function(id){
            var mySelf = this;
            var displayOptions = mySelf.originOptions;
            var selectedList = mySelf.selectedList;
            var selectedIdList = mySelf.selectedIdList;
            //在原始数组里找 找到后1.添加到selectedList
            //若selectedIdList存在 则删除
            if (selectedIdList.indexOf(id)!=-1){
                mySelf.multipleRemove(id);
                return;
            }

            for (var i=0;i<displayOptions.length;i++){
                var item = displayOptions[i]
                if (item.id == id){
                    selectedList.push(item);
                    selectedIdList.push(id);
                    mySelf.multipleInitSearch();
                    mySelf.multipleSearch();
                    mySelf.dispatchData();
                    mySelf.searchInputFocus();
                }
            }

        },
        dispatchData: function(){
            // console.log('派发！！');
            this.$dispatch('selected', this.selectedList);
        },
        multipleRemove: function(id){
            console.log('删除！' + id)
            var mySelf = this;
            var selectedList = mySelf.selectedList;
            var selectedIdList = mySelf.selectedIdList;
            for (var i=0;i<selectedList.length;i++){
                var item = selectedList[i]
                if (item.id == id){
                    //1.从selectedList中删除 2.从selectedIdList中删除
                    selectedList.splice(i,1);
                    var index = selectedIdList.indexOf(item.id)
                    selectedIdList.splice(index,1)
                    mySelf.multipleInitSearch();
                    mySelf.multipleSearch();
                    mySelf.dispatchData();
                    mySelf.searchInputFocus();
                    return;
                }
            }
        },
        multipleSearch: function(event){
            var mySelf = this;
            var search = mySelf.search;
            var REG_RULE = new RegExp(search,"i") //根据用户输入值做正则
            // console.log(REG_RULE)
            //inputDom.style.width = (search.length*0.6) + 'em'
            
            var originOptions = mySelf.originOptions;
            var displayOptions = mySelf.displayOptions;

            // 通过回车键 添加
            if (event && event.keyCode==13 && search!=''){
                console.log('回车！');
                console.log('通过回车添加的值' + search);

                for (var i=0;i<originOptions.length;i++){
                    var item = originOptions[i]
                    if (item.name == search){
                        mySelf.multipleSelect(item.id);
                        return;
                    }
                    else if(i == (originOptions.length-1)){
                        alert('不存在的选项！');
                        return;
                    }
                }
            }

            //将展示列表置空 然后用正则去原始列表中匹配
            mySelf.displayOptions = [];
            //正则表达 匹配搜索字符
            for (var i=0;i<originOptions.length;i++){
                var item = originOptions[i]
                if (REG_RULE.test(item.name)){
                    mySelf.displayOptions.push(item)
                    // console.log(JSON.stringify(item))
                }
            }
            // console.log(JSON.stringify(mySelf.multiple.displayOptions))
        },
        multipleInitSearch: function(){
            var mySelf = this;
            //重置搜索框 1.清空搜索数据 2.下拉框展示全量
            mySelf.search = '';
        },
        blur: function(){
            console.log('hide multiple！！')
            this.show = false;
            this.search = '';
        }

	},
	template: 
        '<div class="functional-select-wrapper" v-on:click.stop="multipleFocus()">' +
            '<label class="display-container multiple-select-container clearfix" v-bind:class="(show)?\'single-selected-focus\':\'\'">' +
                '<p v-show="selectedList.length == 0 ">' +
                    '<span v-if="originOptions.length != 0 ">请选择</span>' +
                    '<span v-else>没有选项</span>' +
                '</p>' +
                '<p class="multiple-selected-item" v-for="item in selectedList" track-by="$index">{{ item.name }}<i v-on:click.stop.prevent="multipleRemove(item.id)">×</i></p>' +
                '<i class="drop" v-bind:class="(show)?\'drop-up\':\'\'">▼</i>' +
            '</label>' +
            '<div class="options-container" v-show="show">' +
                '<div class="search-container">' +
                    '<input placeholder="search here" class="search-input" v-model="search" v-on:keyup="multipleSearch($event)" v-on:click.stop />' +
                '</div>' +
                '<ul class="options-ul-list">' +
                    '<li v-show="displayOptions.length == 0">没有查询到数据</li>' +
                    '<li v-for="item in displayOptions" v-on:click.stop.prevent="multipleSelect(item.id)" v-bind:class=" selectedIdList.indexOf(item.id)!=-1?\'selected\':\'\' ">{{ item.name }}</li>' +
                '</ul>' +
            '</div>' +
        '</div>'
})


// 自定义输入多个
Vue.component('custom-multiple-input', {
	props: ['selecteddata'],
	data: function() {
		var data = {
            optionsList: [],
            show: false,
            search: "",
            selectedList: []
		}
		return data
	},
	computed:{
		// selectedFocus:function(){
		// 	return {
		// 		"single-selected-focus": this.show
		// 	} 
		// },
		// dropUp: function(){
		// 	return {
		// 		"drop-up": this.show
		// 	}
		// }
	},
	ready: function(){
		window.addEventListener('click',this.blur)
	},
	// events: {
	// 	// ajax获取originOptions后 初始化
	// 	initCustomMultiple: function(data){
	// 		this.selectedList = data
	// 	}

	// },
    watch: {

        selecteddata: function(val, oldVal){
            // console.log('selected old: ' + JSON.stringify(oldVal))
            // console.log('selectednew: ' + JSON.stringify(val))
            this.selectedList = val
        }

    },
	methods:{
        focus: function(){
            if (!this.show){
                document.body.click();
                console.log('自定义输入 下拉!!!')
                var mySelf = this;
                mySelf.show = true;
                mySelf.searchInputFocus();
            }
            else{
                this.blur();
            }
        },
        searchInputFocus: function(){
            var searchInput = this.$el.getElementsByClassName('search-input')[0];

            this.$nextTick(function(){
                searchInput.focus();
            })

        },
        select: function(name){
            var mySelf = this;
            var search = mySelf.search;
            var selectedList = mySelf.selectedList;

            //若selectedIdList存在该search 则删除   不存在 则添加
            var index = selectedList.indexOf(name);
            // console.log(index)
            // console.log(equipmentName)
            // console.log(JSON.stringify(selectedList))
            // console.log(index)
            if (index !=-1 ){
                mySelf.remove(index);
                return;
            }
            else{
                selectedList.push(search);
                mySelf.initSearch();
                mySelf.optionsList = [];
                mySelf.searchInputFocus();
                mySelf.dispatchData();
            }
        },
        dispatchData: function(){
            //console.log('派发啊！！')
            this.$dispatch('inputed', this.selectedList)
        },
        remove: function(index){
            var mySelf = this;
            var selectedList = mySelf.selectedList;

            selectedList.splice(index,1);
            mySelf.initSearch();
            mySelf.optionsList = [];
            mySelf.searchInputFocus();
            mySelf.dispatchData();
        },
        doSearch: function(event){
            var mySelf = this;
            var search = mySelf.search;
            var selectedList = selectedList;

            mySelf.optionsList = []
            mySelf.optionsList.unshift(search)

            // console.log(JSON.stringify(search))
            // console.log(JSON.stringify(mySelf.optionsList))
            // console.log(JSON.stringify(mySelf.recordModal.equipment.optionsList))

            if (event.keyCode==13){
                mySelf.select(search)
                // mySelf.initSearch();
                // optionsList = []
            }
        },
        initSearch: function(){
            var mySelf = this;
            mySelf.search = '';
        },
        blur: function(){
        	this.show = false;
        	this.search = '';
        }
	},
	template: 
        '<div class="functional-select-wrapper" v-on:click.stop="focus()">' +
            '<label class="display-container multiple-select-container clearfix" v-bind:class="(show)?\'single-selected-focus\':\'\'">' +
                '<p v-show="selectedList.length == 0 ">请输入设备</p>' +
                '<p class="multiple-selected-item" v-for="item in selectedList" track-by="$index">{{ item }}<i v-on:click.stop.prevent="remove($index)">×</i></p>' +
                '<i class="drop" v-bind:class="(show)?\'drop-up\':\'\'">▼</i>' +
            '</label>' +
            '<div class="options-container" v-show="show">' +
                '<div class="search-container">' +
                    '<input placeholder="在这里填写内容" class="search-input" v-model="search" v-on:keyup="doSearch($event)" v-on:click.stop/>' +
                '</div>' +
                '<ul class="options-ul-list" v-show="search!=\'\'">' +
                    '<li v-for="item in optionsList" v-on:click.stop="select(item)">{{ item }}</li>' +
                '</ul>' +
            '</div>' +
        '</div>'

})


// 多选（数据格式简化 非json）
Vue.component('multiple-select-simplify', {
    props: ['optionsdata','selecteddata'],
    data: function() {
        var data = {
            originOptions: [],
            displayOptions: [],
            show: false,
            search: '',
            selectedList: []
        }
        return data
    },
    computed:{
        // selectedFocus:function(){
        //  return {
        //      "single-selected-focus": this.show
        //  } 
        // },
        // dropUp: function(){
        //  return {
        //      "drop-up": this.show
        //  }
        // }
    },
    ready: function(){
        window.addEventListener('click',this.blur)
    },
    watch: {
        optionsdata: function (val, oldVal) {
            // console.log('option old: ' + JSON.stringify(oldVal))
            // console.log('option new: ' + JSON.stringify(val))
            this.show = false;
            this.originOptions = val;
        },
        selecteddata: function(val, oldVal){
            // console.log('selected old: ' + JSON.stringify(oldVal))
            // console.log('selectednew: ' + JSON.stringify(val))
            this.selectedList = val
        }

    },
    // events: {
    //     // ajax获取originOptions后 初始化
    //     initMultipleSimplify: function(originOptions,selectedList){
    //         this.originOptions = originOptions;
    //         this.selectedList = [];
    //         this.show = false;
    //         this.multipleInitSearch();
            
    //         if(selectedList){
    //             // this.selectedList = selectedList
    //             for (var i=0;i<selectedList.length;i++){
    //                 var item = selectedList[i];
    //                 this.multipleSelect(item);
    //             }
    //         }
    //     }

    // },
    methods:{
        multipleFocus: function(){

            if (!this.show){
                document.body.click();
                console.log('multiple-simplify show');
                this.show = true;
                this.multipleSearch();
                this.searchInputFocus();
            }
            else{
                this.blur();
            }

        },
        multipleSelect: function(name){
            var mySelf = this;
            var name = name;
            var displayOptions = mySelf.originOptions;
            var selectedList = mySelf.selectedList;
            //在原始数组里找 找到后1.添加到selectedList
            //若selectedIdList存在 则删除
            if (selectedList.indexOf(name)!=-1){
                mySelf.multipleRemove(name);
                return;
            }

            for (var i=0;i<displayOptions.length;i++){
                var item = displayOptions[i]
                if (name == item){
                    selectedList.push(item);
                    mySelf.multipleInitSearch();
                    mySelf.multipleSearch();
                    mySelf.searchInputFocus();
                    mySelf.dispatchData();
                }
            }
            
        },
        searchInputFocus: function(){
            var searchInput = this.$el.getElementsByClassName('search-input')[0];

            this.$nextTick(function(){
                searchInput.focus();
            })

        },
        dispatchData: function(){
            console.log('派发！！')
            this.$dispatch('selected', this.selectedList)
        },
        multipleRemove: function(name){
            console.log('删除！' + name)
            var mySelf = this;
            var name = name;
            var selectedList = mySelf.selectedList;
            for (var i=0;i<selectedList.length;i++){
                var item = selectedList[i]
                if (item == name){
                    //1.从selectedList中删除 2.从selectedIdList中删除
                    var index = selectedList.indexOf(item)
                    selectedList.splice(index,1)
                    mySelf.multipleInitSearch();
                    mySelf.multipleSearch();
                    mySelf.searchInputFocus();
                    mySelf.dispatchData();
                    return;
                }
            }
        },
        multipleSearch: function(event){
            var mySelf = this;
            var search = mySelf.search;
            var REG_RULE = new RegExp(search,"i") //根据用户输入值做正则
            // console.log(REG_RULE)
            //inputDom.style.width = (search.length*0.6) + 'em'
            
            var originOptions = mySelf.originOptions;
            var displayOptions = mySelf.displayOptions;

            // 通过回车键 添加
            if (event && event.keyCode==13 && search!=''){
                console.log('回车！');
                console.log('通过回车添加的值' + search);

                for (var i=0;i<originOptions.length;i++){
                    var item = originOptions[i]
                    if (item == search){
                        mySelf.multipleSelect(item);
                        return;
                    }
                    else if(i == (originOptions.length-1)){
                        alert('不存在的选项！');
                        return;
                    }
                }
            }

            //将展示列表置空 然后用正则去原始列表中匹配
            mySelf.displayOptions = [];
            //正则表达 匹配搜索字符
            for (var j=0;j<originOptions.length;j++){
                var item = originOptions[j]
                if (REG_RULE.test(item)){
                    mySelf.displayOptions.push(item)
                    // console.log(JSON.stringify(item))
                }
            }
            // console.log(JSON.stringify(mySelf.multiple.displayOptions))
        },
        multipleInitSearch: function(){
            var mySelf = this;
            //重置搜索框 1.清空搜索数据 2.下拉框展示全量
            mySelf.search = '';
        },
        blur: function(){
            console.log('hide multiple-simplify！！')
            this.show = false;
            this.search = '';
        }

    },
    template: 
        '<div class="functional-select-wrapper" v-on:click.stop="multipleFocus()">' +
            '<label class="display-container multiple-select-container clearfix" v-bind:class="(show)?\'single-selected-focus\':\'\'">' +
                '<p v-show="selectedList.length == 0 ">' +
                    '<span v-if="originOptions.length != 0 ">请选择</span>' +
                    '<span v-else>没有选项</span>' +
                '</p>' +
                '<p class="multiple-selected-item" v-for="item in selectedList" track-by="$index">{{ item }}<i v-on:click.stop.prevent="multipleRemove(item)">×</i></p>' +
                '<i class="drop" v-bind:class="(show)?\'drop-up\':\'\'">▼</i>' +
            '</label>' +
            '<div class="options-container" v-show="show">' +
                '<div class="search-container">' +
                    '<input placeholder="search here" class="search-input" v-model="search" v-on:keyup="multipleSearch($event)" v-on:click.stop />' +
                '</div>' +
                '<ul class="options-ul-list">' +
                    '<li v-show="displayOptions.length == 0">没有查询到数据</li>' +
                    '<li v-for="item in displayOptions" v-on:click.stop.prevent="multipleSelect(item)" v-bind:class=" selectedList.indexOf(item)!=-1?\'selected\':\'\' ">{{ item }}</li>' +
                '</ul>' +
            '</div>' +
        '</div>'
})