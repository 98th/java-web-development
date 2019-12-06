$('input[name="choose"]').click(function(e) {
    if(e.target.value === 'driver') {
        $('#optional').show();
    } else {
        $('#optional').hide();
    }
})

$('#optional').hide();