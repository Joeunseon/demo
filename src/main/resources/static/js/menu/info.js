/**
 * Info.js (menu)
 */
const ENDPOINTS = {
    info: '/api/menu/',
    children: '/api/menus/{parentSeq}/children'
};

const ELEMENTS = {
    liInfo: $('.li_info').detach(),
    liNone: $('.li_none').detach(),
    item: $('.accordion-item').detach(),
    itemNone: $('.accordion_none').detach()
}

$(document).ready(function () {
    infoInit();
});

function infoInit() {
    const menuSeq = $('#menuSeq').val();
    if ( menuSeq ) {
        fn_fetchGetData(`${ENDPOINTS.info}${menuSeq}`).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    const result = data.data;
                    $('.menuNm').text(result.menuNm);
                    $('.menuTypeDesc').text(result.menuTypeDesc);
                    $('.menuUrl').text(result.menuUrl != '' ? `(${result.menuUrl})` : '');
                    $('.activeYn').text(result.activeYn);
                    $('.menuOrder').text(result.menuOrder);
                    getChildren(menuSeq);
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

function getChildren(menuSeq) {

    if ( menuSeq ) {
        const childrenUrl = ENDPOINTS.children.replaceAll('{parentSeq}', menuSeq);
        fn_fetchGetData(childrenUrl).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    const result = data.data;
                    result.forEach(info => { 
                        let infoClone =  ELEMENTS.item.clone();

                        infoClone.find('.accordion-header').attr('id', `header${info.menuSeq}`)
                        infoClone.find('.accordion-collapse').attr('id', `div${info.menuSeq}`).attr('aria-labelledby', `header${info.menuSeq}`);
                        infoClone.find('.accordion-button').attr('id', `btn${info.menuSeq}`).attr('aria-controls', `div${info.menuSeq}`).attr('data-bs-target', `#div${info.menuSeq}`);

                        infoClone.find('.level3MenuNm').text(info.menuNm);
                        infoClone.find('.level3MenuUrl').text(info.menuUrl)
                        infoClone.find('.level3MenuTypeDesc').text(info.menuTypeDesc);
                        infoClone.find('.level3MenuOrder').text(info.menuOrder);
                        infoClone.find('.level3ActiveYn').text(info.activeYn);

                        if ( info.children != null ) {
                            info.children.forEach(child => {
                                let childClone = ELEMENTS.liInfo.clone();

                                childClone.find('.level4MenuNm').text(child.menuNm);
                                childClone.find('.level4MenuUrl').text(child.menuUrl);
                                childClone.find('.level4MenuTypeDesc').text(child.menuTypeDesc);
                                childClone.find('.level4MenuOrder').text(child.menuOrder);
                                childClone.find('.level4ActiveYn').text(child.activeYn);

                                infoClone.find('.list-group').append(childClone);
                            });
                        } else {
                            infoClone.find('.list-group').append(ELEMENTS.liNone.clone());
                        }

                        $('#menuAccordion').append(infoClone);
                    });
                } else {
                    $('#menuAccordion').append(ELEMENTS.itemNone.clone());
                }
            } 
        });
    }
}