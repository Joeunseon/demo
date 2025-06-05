/**
 * Info.js (log)
 */

const ENDPOINTS = {
    info: '/api/log/',
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
    infoInit();
});

function infoInit() {
    const logSeq = $('#logSeq').val()
    if ( logSeq ) {
        fn_fetchGetData(`${ENDPOINTS.info}${logSeq}`).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    const result = data.data;
                    $('.errMsg').text(result.errMsg);
                    $('.errLevel').text(result.errLevel);
                    const errLevelClass = classMap[result.errLevel] || '';
                    if ( errLevelClass ) 
                        $('.errLevel').addClass(errLevelClass);
                    
                    $('.errCd').text(result.errCd);
                    $('.occurredDt').text(result.occurredDt);
                    $('.requestMethod').text(result.requestMethod);
                    
                    const methodClass = classMap[result.requestMethod] || '';
                    if ( methodClass ) 
                        $('.requestMethod').addClass(methodClass);
                    
                    $('.requestUrl').text(result.requestUrl);
                    $('.resolvedStat').text(result.resolvedStat);
                    $('.resolvedDt').text(result.resolvedDt ? '('+result.resolvedDt+')' : '' );
                    $('.requestUserNm').text(result.requestUserNm);
                    $('.requestUserId').text(result.requestUserId ? '('+result.requestUserId+')' : '');
                    $('.stackTrace').text(result.stackTrace);
                }
            } else {
                $('#alertModal').find('.modal-body').text($('#errorRequest').val());
                $('#alertModal').modal('show');
                $('#alertModal').on('hidden.bs.modal', function () {
                    history.back();
                });
            }
        });
    } else {
        $('#alertModal').find('.modal-body').text($('#errorRequest').val());
        $('#alertModal').modal('show');
        $('#alertModal').on('hidden.bs.modal', function () {
            history.back();
        });
    }
}

function logValidity() {
    $('#confirmModal .modal-body').html($('#resolveConfirm').val());
    $('#confirmModal .saveBtn').off('click').on('click', resolve);
    $('#confirmModal').modal('show');
}

function resolve() {

    const param = {
        logSeq : $('#logSeq').val()
    }

    fn_fetchPatchData(`${ENDPOINTS.resolve}`, param)
        .then(data => {
            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
            
            if ( data.result ) {
                $('#alertModal').on('hidden.bs.modal', function () {
                    infoInit();
                });
            }
        });
}