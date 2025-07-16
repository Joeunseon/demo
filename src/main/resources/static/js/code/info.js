/**
 * Info.js (code)
 */

const ENDPOINTS = {
    grpApi: '/api/code-group/',
    cdApi: '/api/code/',
    list: '/code/list'
};

const ELEMENTS = {
    none: $('.code_none').detach(),
    info: $('.code_info').detach()
}

$(document).ready(function () {
    infoInit();
});

function infoInit() {

    if ( $('#grpSeq').val() ) {
        fn_fetchGetData(`${ENDPOINTS.grpApi}${$('#grpSeq').val()}`).then(data => {
            if ( data.data ) {
                if ( data.data != null ) {
                    const result = data.data;
                    $('.grpNm').text(result.grpNm);
                    $('.grpCd').text(result.grpCd);
                    $('.grpDesc').text(result.grpDesc);
                    $('.useYn').text(result.useYn);
                    console.log(result.children)
                    result.children.forEach(info => {
                        let infoClone =  ELEMENTS.info.clone();
                        infoClone.find('.cd').text(info.cd);
                        infoClone.find('.cdNm').text(info.cdNm);
                        infoClone.find('.cdDesc').text(info.cdDesc);
                        infoClone.find('.useYn').text(info.useYn);

                        $('.code_div').append(infoClone);
                    });

                    if ( result.children == null || result.children.length == 0 )
                        $('.code_div').append(ELEMENTS.none.clone());
                }
            } else {
                $('#alertModal').find('.modal-body').text($('#errorRequest').val());
                $('#alertModal').modal('show');
                $('#alertModal').on('hidden.bs.modal', function () {
                    history.back();
                });
            }
        });
    } else if ( $('#cdSeq').val() ) {
        fn_fetchGetData(`${ENDPOINTS.cdApi}${$('#cdSeq').val()}`).then(data => {
            if ( data.data ) {
                if ( data.data != null ) {
                    const result = data.data;
                    $('.cd').text(result.cd);
                    $('.cdNm').text(result.cdNm);
                    $('.cdDesc').text(result.cdDesc);
                    $('.useYn').text(result.useYn);
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