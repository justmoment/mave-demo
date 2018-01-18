var dataTable = {
    initTable : function(tableId,url,paramMap,dataColumns,dataColumnDefs,callback) {
        //get the DataTable object
        var otable =$("#"+tableId).dataTable();
        //destroy the DataTable if exists so as to reinitialize it.
        if(otable) {
            otable.fnDestroy();
        }
        /*$("#"+tableId).on('order.dt', callback())
         .on('page.dt', callback())*/

        $("#"+tableId).dataTable({
            "processing": true,
            'serverSide': true,
            "pagingType": "full_numbers",
            "searching":false,
            "lengthMenu": [ [10,15,20,50,100], [10,15,20,50,100] ],
            "language": {
                "url": "language.js"
            },
            'ajax' : {
                'url' : url,
                'dataType': 'jsonp',
                'type': 'POST',
                'contentType': 'application/json; charset=utf-8',
                'data': function (d) {
                    console.log(d);
                    d.paramMap = paramMap;
                    return JSON.stringify(d);
                }
            },
            columns:dataColumns,
            columnDefs:dataColumnDefs,
            order: [[ 0, 'asc' ]],
            "drawCallback": function( settings ) {
                callback();
            }
        });
        /*.on( 'draw.dt', callback());*/
    }
};