/**
 * Regist.js (file)
 */
const FILE_ENDPOINT = '/api/file';
let myDropzone;

$(document).ready(function () {

    Dropzone.autoDiscover = false;

    let dropzoneElement = document.querySelector('.dropzone');

    if ( dropzoneElement ) {
        if (dropzoneElement.dropzone) {
            dropzoneElement.dropzone.destroy();
        }

        myDropzone = new Dropzone(dropzoneElement, {
            url: FILE_ENDPOINT, // 업로드 서버 URL
            method: 'post',
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
                        if ( response.data != null ) 
                            $('#fileSeq').val(response.data);

                        if (oThis.callback)  oThis.callback();
                    }
                });

                oThis.on("sending", function (file, xhr, formData) {

                    formData.append("file", file);
                });

                oThis.on("addedfile", function (file) {
                    
                });
            }
        });
    }

});

function uploadFiles(callback) {

    if ( myDropzone.files.length > 0 ) {
        // 콜백을 Dropzone 내부에서 실행하도록 설정
        myDropzone.callback = callback;
        // 업로드 처리
        myDropzone.processQueue();
    } else {
        if ( callback ) callback();
    }
}