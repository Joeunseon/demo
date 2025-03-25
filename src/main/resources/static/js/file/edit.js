/**
 * Edit.js (file)
 */
const FILE_ENDPOINTS = {
    info: '/api/file/',
    edit: '/api/file'
};
const FILE_ELEMENTS = {
    liInfo: $('.li_info').detach()
}

let myDropzone;

$(document).ready(function () {

    Dropzone.autoDiscover = false;

    let dropzoneElement = document.querySelector('.dropzone');

    if ( dropzoneElement ) {
        if (dropzoneElement.dropzone) {
            dropzoneElement.dropzone.destroy();
        }

        myDropzone = new Dropzone(dropzoneElement, {
            url: FILE_ENDPOINTS.edit, // 업로드 서버 URL
            method: 'patch',
            autoProcessQueue: false, // 자동 업로드 여부
            clickable: true, // 클릭 기능 여부
            autoQueue: true, // 드래그 드랍 후 바로 서버 전송 여부
            uploadMultiple: true, // 다중 업로드 여부
            addRemoveLinks: true, // 파일 삭제 버튼
            dictRemoveFile: '삭제', // 파일 삭제 버튼 표시 텍스트
            maxFiles: 10, // 업로드 파일 수
            maxFilesize: 32, // 최대 업로드 용량
            init: function () {
                let oThis = this;
        
                $('#fileBtn').on('click', function (e) {
                    e.preventDefault();
                    dropzoneElement.click();
                });

                oThis.on('successmultiple', function (files, response) {
                    if ( response.result ) {
                        if ( response.data != null ) {
                            $('#fileSeq').val(response.data);
                        } else {
                            $('#fileSeq').val('');
                        }

                        if (oThis.callback)  oThis.callback();
                    }
                });

                oThis.on("sending", function (file, xhr, formData) {
                    const dto = {
                        fileSeq: $('#fileSeq').val(),
                        dtlSeq: $('.li_info').map((_, el) => $(el).find('.oriNm').data('dtlSeq')).get()
                    };
                
                    // dto 객체를 JSON으로 감싸서 Blob 처리
                    formData.append("dto", new Blob([JSON.stringify(dto)], {
                        type: "application/json"
                    }));

                    formData.append("file", file);
                });

                oThis.on("addedfile", function (file) {
                    
                });
            }
        });
    }

});

function fileInit(fileSeq) {
    if ( fileSeq ) {
        fn_fetchGetData(`${FILE_ENDPOINTS.info}${fileSeq}`).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    data.data.forEach(info => {
                        let infoClone =  FILE_ELEMENTS.liInfo.clone();
                        infoClone.find('.oriNm').text(info.oriNm).data('dtlSeq', info.dtlSeq);
                        const sizeInKB = Math.round(info.fileSize / 1024);
                        infoClone.find('.fileSize').text(sizeInKB + ' KB');
            
                        $('.fileList').append(infoClone);
                    });
                }
            }
        });
    }
}

function delLi(el) {

    $(el).closest('.li_info').remove();
}

function uploadFiles(callback) {

    if ( myDropzone.files.length > 0 ) {
        // 콜백을 Dropzone 내부에서 실행하도록 설정
        myDropzone.callback = callback;
        // 업로드 처리
        myDropzone.processQueue();
    } else {
        const formData = new FormData();

        const dto = {
            fileSeq: $('#fileSeq').val(),
            dtlSeq: $('.li_info').map((_, el) => $(el).find('.oriNm').data('dtlSeq')).get()
        };

        formData.append("dto", new Blob([JSON.stringify(dto)], {
            type: "application/json"
        }));

        fetch(FILE_ENDPOINTS.edit, {
            method: 'PATCH',
            body: formData
        })
        .then(data => data.json())
        .then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    $('#fileSeq').val(data.data);
                } else {
                    $('#fileSeq').val('');
                }

                if ( callback ) callback();
            }
        });
    }
}