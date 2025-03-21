/**
 * Info.js (file)
 */

const FILE_ELEMENTS = {
    liInfo: $('.li_info').detach()
}

const FILE_ENDPOINTS = {
    info: '/api/file/',
    down: '/api/file/download/',
    zipDown: '/api/file/download/zip'
}

function fileInfo(fileSeq) {

    if ( fileSeq ) {
        fn_fetchGetData(`${FILE_ENDPOINTS.info}${fileSeq}`).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    $('#zipDownloadBtn').data('fileSeq', fileSeq);

                    data.data.forEach(info => {
                        let infoClone =  FILE_ELEMENTS.liInfo.clone();
                        infoClone.find('.oriNm').text(info.oriNm).attr('href', `${FILE_ENDPOINTS.down}${info.dtlSeq}`);
                        const sizeInKB = Math.round(info.fileSize / 1024);
                        infoClone.find('.fileSize').text(sizeInKB + ' KB');
            
                        $('.fileList').append(infoClone);
                    });
                } else {
                    $('#fileInfo').remove();
                }
            }
        });
    } else {
        $('#fileInfo').remove();
    }
}

function fileDownZip() {
    
}