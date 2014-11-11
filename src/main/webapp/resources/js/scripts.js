$( document ).ready(function() {
    $('#register-parcel').click(function () {
        $('#parcel-registration').toggle();
    });

    $('#register-parcel-cs').click(function () {
        $('#parcel-registration-cs').toggle();
    });

    $('#edit-parcel').click(function() {
        $('#parcel-editing').toggle();
    });

    $('#register-order-shipment').click(function() {
        $('#order-shipment-registration').toggle();
    });

    $('#register-payment').click(function() {
        $('#payment-registration').toggle();
    });
});