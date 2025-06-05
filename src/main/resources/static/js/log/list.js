/**
 * List.js (log)
 */

const ELEMENTS = {
    trNone: $('.tr_none').detach(),
    trInfo: $('.tr_info').detach()
}

const ENDPOINTS = {
    info: '/log/info',
    resolve: '/api/log/resolve'
}

const classMap = {
    '정보': '',
    '경고': 'text-warning',
    '오류': 'text-danger',
    '심각': 'text-danger',
    'POST': 'text-success',
    'GET': 'text-primary',
    'PATCH': 'text-info',
    'DELETE': 'text-danger'
};

$(document).ready(function () {
    listInit();
    
    $('#searchStartDt, #searchEndDt').on('change', function() {
        let startDt = $('#searchStartDt').val();
		let endDt = $('#searchEndDt').val();
        
        if ( startDt && endDt ) {
            startDt = Number(startDt.replace(/-/gi, ''));
            endDt = Number(endDt.replace(/-/gi, ''));
            
            if ( startDt > endDt ) {
                $('#alertModal').find('.modal-body').text($('#dateValidation').val());
                $('#alertModal').modal('show');
                $(this).val('');
            }
        }
	});
    
    $('#searchKeyword').on('keyup', function(key) {
        if ( key.keyCode == 13 )
			searchList();
    });
    
    $(document).on('click', '[data-role="btnGoPage"]', function (e) {
        let page = $(this).data('page');
        
        if ( $('#page').val() != page ) {
            $('#page').val(page);
            listInit();
        }
    });

    $(document).on('click', 'input[type=checkbox]', function(e) {
        const oThis = this;
        if ( $(oThis).hasClass('checkAll') ) {
            $('input[type=checkbox]').prop('checked', $(oThis).is(':checked'));
        } else {
            if ( ($('input[type=checkbox]').length - 1) == $('.logSeq:checked').length ) {
                $('.checkAll').prop('checked', true);
            } else {
                $('.checkAll').prop('checked', false);
            }
        }
    });
});

function listInit() {
    fn_resultList('searchForm', drawResultList);
}

function searchList() {
    $('#page').val(1);
    fn_resultList('searchForm', drawResultList);
}

function drawResultList(data) {
    $('.listFragment').find('tbody').empty();
    $('.listFragment').find('.totalCnt').text(data.totalCnt);
    $('#page').val(data.currentPage);

    if ( data.totalCnt > 0 ) {
        data.resultList.forEach(info => {
            let infoClone =  ELEMENTS.trInfo.clone();
            infoClone.find('.logSeq').val(info.logSeq);
            infoClone.find('.rownum').text(info.rownum);
            infoClone.find('.errCd').text(info.errCd);
            infoClone.find('.errLevel').text(info.errLevel);
            const errLevelClass = classMap[info.errLevel] || '';
            if ( errLevelClass ) 
                infoClone.find('.errLevel').addClass(errLevelClass);
            const errMsg = info.errMsg;
            infoClone.find('.errMsg').text(errMsg.length > 65 ? errMsg.substring(0, 65) + " ..." : errMsg).attr('href', `${ENDPOINTS.info}?logSeq=${info.logSeq}`);
            infoClone.find('.requestMethod').text(info.requestMethod);
            const methodClass = classMap[info.requestMethod] || '';
            if ( methodClass ) 
                infoClone.find('.requestMethod').addClass(methodClass);
            infoClone.find('.occurredDt').text(info.occurredDt);
            infoClone.find('.resolvedStat').text(info.resolvedStat);

            $('.listFragment').find('tbody').append(infoClone);
        });
    } else {
        $('.listFragment').find('tbody').append(ELEMENTS.trNone.clone());
    }
}

function logValidity() {
    if ( $('.logSeq:checked').length == 0 ) {
        $('#alertModal').find('.modal-body').text($('#checkValidation').val());
        $('#alertModal').modal('show');
        return;
    }

    $('#confirmModal .modal-body').html($('#resolveConfirm').val());
    $('#confirmModal .saveBtn').off('click').on('click', resolve);
    $('#confirmModal').modal('show');
}

function resolve() {
    let logSeqList = new Array();
    $('.logSeq:checked').each(function (e) {
        logSeqList.push($(this).val());
    });
    
    let param = {
        'logSeqList' : logSeqList
    };

    fn_fetchPatchData(`${ENDPOINTS.resolve}`, param)
        .then(data => {
            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
            
            if ( data.result ) {
                $('#alertModal').on('hidden.bs.modal', function () {
                    listInit();
                    $('.checkAll').prop('checked', false);
                });
            }
        });
}