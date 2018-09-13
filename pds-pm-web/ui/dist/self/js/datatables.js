$.fn
		.extend({

			getSelectedRow : function() {
				var $wrap=$(this.selector).parents('.dataTables_scroll');
				var list = $wrap.data("datatables-checked");
				if (list) {
					return list.val();
				}
				return [];
			},
			setSelectedRow : function(ids, iCheck) {
				var $wrap=$(this.selector).parents('.dataTables_scroll');
				if (!!ids && $.isArray(ids)) {
					if (iCheck == undefined) {
						iCheck = true;
					}
					var tbody = $wrap.find('tbody');
					$wrap.find('thead').find(
							'input[type="checkbox"].minimal').filter(
							'[name="all"]').iCheck("uncheck");
					for ( var i in ids) {
						var ic = tbody.find('input[type="checkbox"].minimal')
								.filter('[name="sub"]').filter(
										'[value="' + ids[i] + '"]');
						if (iCheck) {
							ic.iCheck("check");
						} else {
							ic.iCheck("uncheck");
						}
					}
				} else {
					if (window.console && window.console.error) {
						window.console.error("setSelectedRow 参数ids 必须为数组")
					}
				}
			},

			getAllClickedRowData : function() {
				var $wrap=$(this.selector).parents('.dataTables_scroll');
				var checkedRowData = [];
				var list = $wrap.data("datatables-checked").val();
				var table = $wrap.DataTable();
				// var allData = table.data().toArray();
				table.data().each(function(d) {
					for (var i = 0; i < list.length; i++) {
						if (d.id == list[i]) {
							checkedRowData.push(d);
						}
					}
				});
				return checkedRowData;
			},
			getSelectedRowData : function(id) {
				var $wrap=$(this.selector).parents('.dataTables_scroll');
				var data = null;
				var table = $wrap.DataTable();
				table.data().each(function(d) {
					if (d.id == id) {
						data = d;
						return data;
					}
				});
				return data;
			},
			/**
			 * { selected:true, checkbox:false, createdRow:function(){},
			 * serverSide:true, processing:true, ops:{
			 * view:function(id,rowData){ }, edit:function(id,rowData){ },
			 * del:function(id,rowData){ } },
			 * callAfterDrawn:function(data,opts){ }, createdRow: function (
			 * row, data, index ){ } }
			 */
			initDataTable : function(options) {

				var defaultOpts = {
					selected : true,
					paging : true,
					checkbox : true,
					index : true, // default close idnex column funciton. 
					createdRow : function() {
					}
				}
			

				options = $.extend({}, defaultOpts, options);

				var infoString=", 共  _TOTAL_  条记录";
				var outString="tr" + "<'row'<'col-sm-5'li><'col-sm-7'p>>";
				if(!options.paging){
					infoString=""
					// outString=""
				}

				options.serverSide = true;
				options.processing = true;
				var viewFlag = options.viewFlag;
				var editFlag = options.editFlag;
				var delFlag = options.delFlag;
				var publicFlag = options.publicFlag;
				var topFlag = options.topFlag;
				var cancelTopFlag = options.cancelTopFlag;

				var _columns = []
				var checkboxColumns = [ {
					"orderable" : false,
					"data" : null,
					"width" : "2%",
					"title" : '<input class="minimal" name="all" value="0" type="checkbox" />',
					"render" : function(data, type, row, meta) {
						return '<input class="minimal" name="sub" value="'
								+ row.id + '" type="checkbox" />';
					}
				} ];
				var opsColumns = [];
				if (options.ops) {

					var opsGenColumns = [ {
						"orderable" : false,
						"data" : null,
						"width" : "8%",
						"title" : '操作',
                        "class" : "content-center",
						"render" : function(data, type, row, meta) {
							var _ops = options.ops;
							var viewHtml = '';
							var rowId = row.id;
							var projectId = row.projectId;
							// if (_ops.view && projectAdd != null) {
							if (_ops.view && viewFlag) {
								// viewHtml='<button data-rowId="'+rowId+'"
								// id="row_view_btn_'+rowId+'"
								// name="row_view_btn" type="button" class="btn
								// btn-primary btn-sm table_btn">详情</button>';
								viewHtml = '<a style="margin-right: 15px;" data-rowId="'
										+ rowId
										+ '" id="row_view_btn_'
										+ rowId
										+ '" name="row_view_btn" class="col-blue">详情</a>';
							}
							var editHtml = '';
							if (_ops.edit && editFlag) {
								// editHtml='<button data-rowId="'+rowId+'"
								// id="row_edit_btn_'+rowId+'"
								// name="row_edit_btn" type="button" class="btn
								// btn-primary btn-sm table_btn">修改</button>';
								editHtml = '<a style="margin-right: 15px;" data-rowId="'
										+ rowId
										+ '"'
										+ ' data-rowNum="'
										+ meta.row
										+ '"'
										+ ' data-colNum="'
										+ meta.col
										+ '" id="row_edit_btn_'
										+ rowId
										+ '" name="row_edit_btn" class="col-green">修改</a>';
							}
							var delHtml = '';
							if (_ops.del && delFlag) {
								// delHtml='<button data-rowId="'+rowId+'"
								// id="row_del_btn_'+rowId+'" name="row_del_btn"
								// type="button" class="btn btn-sm table_btn
								// btn-danger">删除</button>';
								delHtml = '<a style="margin-right: 15px;" data-rowId="'
										+ rowId
										+ '" id="row_del_btn_'
										+ rowId
										+ '" name="row_del_btn" class="col-red">删除</a>';
							}

							var publishHtml = '';
							if(data.state == '0'){
                                if(_ops.publish && publicFlag){

                                    publishHtml = '<a style="margin-right: 15px;" data-rowId="'
                                        + rowId
                                        + '"'
                                        + ' data-rowNum="'
                                        + meta.row
                                        + '"'
                                        + ' data-colNum="'
                                        + meta.col
                                        + '" id="row_publish_btn_'
                                        + rowId
                                        + '" name="row_publish_btn">发布</a>';

                                }
							}
                            var topHtml = '';

                            if(data.state == '1'){
                            	if(data.isTop =='0' ){
                                    if(_ops.top && topFlag){

                                        topHtml = '<a style="margin-right: 15px;" data-rowId="'
                                            + rowId
                                            + '"'
                                            + ' data-rowNum="'
                                            + meta.row
                                            + '"'
                                            + ' data-colNum="'
                                            + meta.col
                                            + '" id="row_top_btn_'
                                            + rowId
                                            + '" name="row_top_btn">置顶</a>';

                                    }
								}else{
                                    if(_ops.cancelTop && cancelTopFlag){

                                        topHtml = '<a style="margin-right: 15px;" data-rowId="'
                                            + rowId
                                            + '"'
                                            + ' data-rowNum="'
                                            + meta.row
                                            + '"'
                                            + ' data-colNum="'
                                            + meta.col
                                            + '" id="row_cancelTop_btn_'
                                            + rowId
                                            + '" name="row_cancelTop_btn">取消置顶</a>';

                                    }
								}

                            }

                            var finishHtml = '';
                            if(data.state == '0'){
                                if(_ops.finish){

                                    finishHtml = '<a style="margin-right: 15px;" data-rowId="'
                                        + rowId
                                        + '"'
                                        + ' data-rowNum="'
                                        + meta.row
                                        + '"'
                                        + ' data-colNum="'
                                        + meta.col
                                        + '" id="row_finish_btn_'
                                        + rowId
                                        + '" name="row_finish_btn">完成</a>';

                                }
                            }

                            var processHtml = '';
                            if(data.operationType =='0'){
                                if(_ops.process){

                                    processHtml = '<a style="margin-right: 15px;" data-rowId="'
                                        + projectId
                                        + '"'
                                        + ' data-rowNum="'
                                        + meta.row
                                        + '"'
                                        + ' data-colNum="'
                                        + meta.col
                                        + '" id="row_process_btn_'
                                        + projectId
                                        + '" name="row_process_btn">处理</a>';

                                }
							}

                            var departProjectEditHtml = '';
                            if(data.delOrUpdateFlag == '1'){
                                if(_ops.departProjectEdit){

                                    departProjectEditHtml = '<a style="margin-right: 15px;" data-rowId="'
                                        + rowId
                                        + '"'
                                        + ' data-rowNum="'
                                        + meta.row
                                        + '"'
                                        + ' data-colNum="'
                                        + meta.col
                                        + '" id="row_departProjectEdit_btn_'
                                        + rowId
                                        + '" name="row_departProjectEdit_btn" class="col-green">修改</a>';

                                }
                            }

                            var doDepartProjectDelHtml = '';
                            if(data.delOrUpdateFlag == '1') {
                                if (_ops.departProjectDel) {
                                    // delHtml='<button data-rowId="'+rowId+'"
                                    // id="row_del_btn_'+rowId+'" name="row_del_btn"
                                    // type="button" class="btn btn-sm table_btn
                                    // btn-danger">删除</button>';
                                    doDepartProjectDelHtml = '<a style="margin-right: 15px;" data-rowId="'
                                        + rowId
                                        + '" id="row_departProjectDel_btn_'
                                        + rowId
                                        + '" name="row_departProjectDel_btn" class="col-red">删除</a>';
                                }
                            }

							return viewHtml + editHtml + publishHtml + topHtml + finishHtml + processHtml + delHtml + departProjectEditHtml + doDepartProjectDelHtml;
						}
					} ];
					opsColumns = opsColumns.concat(opsGenColumns);

				}

				var indexColumns = [{
					"searchable": false,
					"orderable": false,
					"width":"30px",
					"render" : function (data, type, row, meta) {
						var startIndex=meta.settings._iDisplayStart;
						var rowIndex=meta.row;
						var rowNumber=startIndex+rowIndex+1;
						return '<div style="text-align: center;width: 100%">'+rowNumber+'</div>';
					}

				}];
		
				if (options.checkbox) {
					_columns = _columns.concat(checkboxColumns);
				} 
				
				if(options.index){
					_columns=_columns.concat(indexColumns);
				}
				
				
				_columns = _columns.concat(options.columns);
				

				if (options.ops) {
					_columns = _columns.concat(opsColumns);
				}

				function DatatableAjax($datatableDom, options) {
					this._options = options;
					this.$wrap = $datatableDom;

					this.ajaxSuccess = function(data, callback) {
						callback(data);
						this.onSelected(data);
						this.onChecked(data);
						this.onOperation(data);
						this.onCallAfterDrawn(data);
						if ($.isFunction(this._options.ajaxCallback)) {
							this._options.ajaxCallback.call(data);
						}
					}

					this.onCallAfterDrawn = function(data) {
						$wrap=this.$wrap;
						if (this._options.callAfterDrawn) {
							this._options.callAfterDrawn(data, this._options);
						}
					}

					this.onSelected = function(data) {
						$wrap=this.$wrap;
						if (this._options.selected) {
							$wrap.find('tbody').off('click', '>tr');
							$wrap
									.find('tbody')
									.children('tr')
									.on(
											'click',
											{
												$table : $wrap
											},
											function() {
												$(this).toggleClass('selected');
												$subChk = $(this)
														.find(
																'input[type="checkbox"].minimal')
														.filter('[name="sub"]');
												if ($(this)
														.hasClass('selected')) {
													$subChk.iCheck('check');
												} else {
													$subChk.iCheck('uncheck');
												}
											});
						}
					}

					this.onChecked = function(data) {
						$wrap=this.$wrap;
						if (this._options.checkbox) {

							var $chk = $wrap
									.find('input[type="checkbox"].minimal');
							var $allChk = $chk.filter('[name="all"]');
							var $subChks = $chk.filter('[name="sub"]');
							$wrap.data("datatables-checked", new List());
							// iCheck for checkbox and radio inputs
							$chk.iCheck({
								checkboxClass : 'icheckbox_minimal-blue',
								radioClass : 'iradio_minimal-blue'
							});

							$allChk.on('ifChecked', function(event) {
								$subChks.iCheck('check');
							});

							$allChk.on('ifUnchecked', function(event) {
								var list = $wrap.data("datatables-checked");
								if (list.size() == $subChks.length) {
									$subChks.iCheck('uncheck');
								}
							});

							$subChks.on('ifChecked', function(event) {
								var id = $(event.target).val();
								var list = $wrap.data("datatables-checked");
								if (list) {
									if (!list.exists(id)) {
										list.add(id);
									}
								} else {
									list = new List();
									list.add(id);
									$wrap.data("datatables-checked", list);
								}

								if (list.size() == $subChks.length) {
									$allChk.iCheck('check');
								}

							});

							$subChks.on('ifUnchecked', function(event) {
								var id = $(event.target).val();
								var list = $wrap.data("datatables-checked");
								if (list) {
									if (list.exists(id)) {
										list.remove(id);
									}
								}
								$allChk.iCheck('uncheck');
							});

						}

					}

					this.onOperation = function(data) {
						$wrap=this.$wrap;
						if (this._options.ops) {
							var _ops = this._options.ops;
							if (_ops.view) {
								$wrap.find('a[name=row_view_btn]').on(
										"click",
										function(event) {
											_ops
													.view(
															$(this).data(
																	"rowid"),
															{});

											event.stopPropagation();
										});
							}

							if (_ops.edit) {
								$wrap.find('a[name=row_edit_btn]').on(
										"click",
										function(event) {
											_ops.edit($(this).data("rowid"),
													{
														rowNum : $(this).data(
																"rownum"),
														colNum : $(this).data(
																"colnum")
													});
											event.stopPropagation();
										});
							}

							if (_ops.del) {
								$wrap.find('a[name=row_del_btn]').on(
										"click",
										function(event) {
											var _rowid = $(this).data("rowid");
											var jc = $.confirm({
												title : false,
												content : '确定删除吗？',
												confirm : function() {
													_ops.del(_rowid, {});
												},
												cancel : function() {
												},
												confirmButton : '确定',
												cancelButton : '取消',
												onOpen : function() {
												}
											});
											jc.passData = {
												rowid : $(event.target).data(
														"rowid")
											};
											event.stopPropagation();
										});
							}
                            if (_ops.publish) {
                                $wrap.find('a[name=row_publish_btn]').on(
                                    "click",
                                    function(event) {
                                        var _rowid = $(this).data("rowid");
                                        var jc = $.confirm({
                                            title : false,
                                            content : '确定要发布吗？',
                                            confirm : function() {
                                                _ops.publish(_rowid, {});
                                            },
                                            cancel : function() {
                                            },
                                            confirmButton : '确定',
                                            cancelButton : '取消',
                                            onOpen : function() {
                                            }
                                        });
                                        jc.passData = {
                                            rowid : $(event.target).data(
                                                "rowid")
                                        };
                                        event.stopPropagation();
                                    });
                            }

                            if(_ops.finish){

                                $wrap.find('a[name=row_finish_btn]').on(
                                    "click",
                                    function(event) {
                                        var _rowid = $(this).data("rowid");
                                        var jc = $.confirm({
                                            title : false,
                                            content : '确定要完成吗？',
                                            confirm : function() {
                                                _ops.finish(_rowid, {});
                                            },
                                            cancel : function() {
                                            },
                                            confirmButton : '确定',
                                            cancelButton : '取消',
                                            onOpen : function() {
                                            }
                                        });
                                        jc.passData = {
                                            rowid : $(event.target).data(
                                                "rowid")
                                        };
                                        event.stopPropagation();
                                    });
							}

                            if(_ops.process){

                                $wrap.find('a[name=row_process_btn]').on(
                                    "click",
                                    function(event) {
                                        _ops.process($(this).data("rowid"),
                                            {
                                                rowNum : $(this).data(
                                                    "rownum"),
                                                colNum : $(this).data(
                                                    "colnum")
                                            });
                                        event.stopPropagation();
                                    });
                            }

                            if (_ops.departProjectEdit) {
                                $wrap.find('a[name=row_departProjectEdit_btn]').on(
                                    "click",
                                    function(event) {
                                        _ops.departProjectEdit($(this).data("rowid"),
                                            {
                                                rowNum : $(this).data(
                                                    "rownum"),
                                                colNum : $(this).data(
                                                    "colnum")
                                            });
                                        event.stopPropagation();
                                    });
                            }

                            if (_ops.departProjectDel) {
                                $wrap.find('a[name=row_departProjectDel_btn]').on(
                                    "click",
                                    function(event) {
                                        var _rowid = $(this).data("rowid");
                                        var jc = $.confirm({
                                            title : false,
                                            content : '确定删除？',
                                            confirm : function() {
                                                _ops.departProjectDel(_rowid, {});
                                            },
                                            cancel : function() {
                                            },
                                            confirmButton : '确定',
                                            cancelButton : '取消',
                                            onOpen : function() {
                                            }
                                        });
                                        jc.passData = {
                                            rowid : $(event.target).data(
                                                "rowid")
                                        };
                                        event.stopPropagation();
                                    });
                            }

                            if (_ops.top) {
                                $wrap.find('a[name=row_top_btn]').on(
                                    "click",
                                    function(event) {
                                        var _rowid = $(this).data("rowid");
                                        var jc = $.confirm({
                                            title : false,
                                            content : '确定要置顶吗？',
                                            confirm : function() {
                                                _ops.top(_rowid, {});
                                            },
                                            cancel : function() {
                                            },
                                            confirmButton : '确定',
                                            cancelButton : '取消',
                                            onOpen : function() {
                                            }
                                        });
                                        jc.passData = {
                                            rowid : $(event.target).data(
                                                "rowid")
                                        };
                                        event.stopPropagation();
                                    });
                            }
                            if(_ops.cancelTop){
                                $wrap.find('a[name=row_cancelTop_btn]').on(
                                    "click",
                                    function(event) {
                                        var _rowid = $(this).data("rowid");
                                        var jc = $.confirm({
                                            title : false,
                                            content : '确定要取消置顶吗？',
                                            confirm : function() {
                                                _ops.cancelTop(_rowid, {});
                                            },
                                            cancel : function() {
                                            },
                                            confirmButton : '确定',
                                            cancelButton : '取消',
                                            onOpen : function() {
                                            }
                                        });
                                        jc.passData = {
                                            rowid : $(event.target).data(
                                                "rowid")
                                        };
                                        event.stopPropagation();
                                    });
							}

						}

					}

					this.ajax = function(data, callback, settings, options,
							$wrap) {
						var HtmlMenuOpt = {
							endpoint : options.url,
							data : options.urlDataFn.apply(),
							success : function(data) {
								if (data.draw == undefined) {
									data = {
										"draw" : 0,
										"data" : data,
										"recordsTotal" : data.length,
										"recordsFiltered" : data.length
									};
								}
								if(!$wrap.hasClass('dataTables_scroll')){
									$wrap=$wrap.parents('.dataTables_scroll');
								}
								new DatatableAjax($wrap, options).ajaxSuccess(
										data, callback);
							},
							page : data.start / data.length,
							size : data.length == -1 ? 100000 : data.length
						};
						httpservice.ajaxEnt.getRequest(HtmlMenuOpt);
					}
				}

				// $wrap=$('#editable');
				var $wrap = $(this.selector);
				var dataTableObj = $(this.selector)
						.DataTable(
								{
									processing : options.processing,
									serverSide : options.serverSide,
									paging : options.paging,
									ajax : function(data, callback, settings) {
										new DatatableAjax().ajax(data,
												callback, settings, options,
												$wrap)
									},
									createdRow : function(row, data, index) {
										if (index % 2 == 0) {
											$(row).addClass('trodd');
										} else {
											$(row).addClass('treven');
										}
										if (options.createdRow) {
											options
													.createdRow(row, data,
															index);
										}

									},
									columns : _columns,
									sPaginationType : "full_numbers",
									language : {
										paginate : {
											first : "首页",
											last : "尾页",
											previous : "&nbsp;",
											next : "&nbsp;"
										},
										// info : "显示  _START_  到  _END_ 条记录, 共  _TOTAL_  条记录",
										info : infoString,
										infoEmpty : "没有数据",
										lengthMenu : "每页 _MENU_ 条",
										infoFiltered : "(从 _MAX_ 条数据中检索)",
										loadingRecords : "Please wait - loading...",
										zeroRecords : "无查询结果",
										processing : "处理中..."
									},
									dom :
									// "<'row'<'col-sm-6'l><'col-sm-6'f>>" +
									// "<'row'<'col-sm-12'tr>>" +
									outString,
									/*
									 * oLanguage: { "sLengthMenu": "每页显示 _MENU_
									 * 条记录", "sZeroRecords": "抱歉， 没有找到",
									 * "sInfo": "显示 _START_ 到 _END_ 条记录, 共
									 * _TOTAL_ 条记录", "sInfoEmpty": "没有数据",
									 * "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
									 * "oPaginate": { "sFirst": "首页",
									 * "sPrevious": "前一页", "sNext": "后一页",
									 * "sLast": "尾页" }, sZeroRecords: "没有检索到数据" },
									 */
									bSort : false,
									searching : false,
									scrollX: true,
									bAutoWidth:false
								});
				return dataTableObj;
			}
		});