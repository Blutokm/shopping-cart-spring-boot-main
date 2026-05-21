$(function(){

    // User Register validation
    var $userRegister=$("#userRegister");

    $userRegister.validate({
        rules:{
            name:{
                required:true,
                lettersonly:true
            },
            email: {
                required: true,
                space: true,
                email: true
            },
            mobileNumber: {
                required: true,
                space: true,
                numericOnly: true,
                minlength: 10,
                maxlength: 12
            },
            password: {
                required: true,
                space: true
            },
            confirmpassword: {
                required: true,
                space: true,
                equalTo: '#pass'
            },
            address: {
                required: true,
                all: true
            },
            city: {
                required: true,
                space: true
            },
            state: {
                required: true,
                space: true
            },
            pincode: {
                required: true,
                space: true,
                numericOnly: true
            },
            img: {
                required: true
            }
        },
        messages:{
            name:{
                required:'Tên không được để trống',
                lettersonly:'Tên không hợp lệ'
            },
            email: {
                required: 'Email không được để trống',
                space: 'Không được chứa khoảng trắng',
                email: 'Email không hợp lệ'
            },
            mobileNumber: {
                required: 'Số điện thoại không được để trống',
                space: 'Không được chứa khoảng trắng',
                numericOnly: 'Số điện thoại không hợp lệ',
                minlength: 'Tối thiểu 10 chữ số',
                maxlength: 'Tối đa 12 chữ số'
            },
            password: {
                required: 'Mật khẩu không được để trống',
                space: 'Không được chứa khoảng trắng'
            },
            confirmpassword: {
                required: 'Xác nhận mật khẩu không được để trống',
                space: 'Không được chứa khoảng trắng',
                equalTo: 'Mật khẩu không khớp'
            },
            address: {
                required: 'Địa chỉ không được để trống',
                all: 'Địa chỉ không hợp lệ'
            },
            city: {
                required: 'Thành phố không được để trống',
                space: 'Không được chứa khoảng trắng'
            },
            state: {
                required: 'Tỉnh/Thành không được để trống',
                space: 'Không được chứa khoảng trắng'
            },
            pincode: {
                required: 'Mã bưu điện không được để trống',
                space: 'Không được chứa khoảng trắng',
                numericOnly: 'Mã bưu điện không hợp lệ'
            },
            img: {
                required: 'Hình ảnh không được để trống'
            }
        }
    });

    // Orders Validation
    var $orders=$("#orders");

    $orders.validate({
        rules:{
            firstName:{
                required:true,
                lettersonly:true
            },
            lastName:{
                required:true,
                lettersonly:true
            },
            email: {
                required: true,
                space: true,
                email: true
            },
            mobileNo: {
                required: true,
                space: true,
                numericOnly: true,
                minlength: 10,
                maxlength: 12
            },
            address: {
                required: true,
                all: true
            },
            city: {
                required: true,
                all: true
            },
            state: {
                required: true,
                all: true
            },
            pincode: {
                required: true,
                space: true,
                numericOnly: true
            },
            paymentType:{
                required: true
            }
        },
        messages:{
            firstName:{
                required:'Tên không được để trống',
                lettersonly:'Tên không hợp lệ'
            },
            lastName:{
                required:'Họ không được để trống',
                lettersonly:'Họ không hợp lệ'
            },
            email: {
                required: 'Email không được để trống',
                space: 'Không được chứa khoảng trắng',
                email: 'Email không hợp lệ'
            },
            mobileNo: {
                required: 'Số điện thoại không được để trống',
                space: 'Không được chứa khoảng trắng',
                numericOnly: 'Số điện thoại không hợp lệ',
                minlength: 'Tối thiểu 10 chữ số',
                maxlength: 'Tối đa 12 chữ số'
            },
            address: {
                required: 'Địa chỉ không được để trống',
                all: 'Địa chỉ không hợp lệ'
            },
            city: {
                required: 'Thành phố không được để trống',
                all: 'Thành phố không hợp lệ'
            },
            state: {
                required: 'Tỉnh/Thành không được để trống',
                all: 'Tỉnh/Thành không hợp lệ'
            },
            pincode: {
                required: 'Mã bưu điện không được để trống',
                space: 'Không được chứa khoảng trắng',
                numericOnly: 'Mã bưu điện không hợp lệ'
            },
            paymentType:{
                required: 'Vui lòng chọn phương thức thanh toán'
            }
        }
    });

    // Reset Password Validation
    var $resetPassword=$("#resetPassword");

    $resetPassword.validate({
        rules:{
            password: {
                required: true,
                space: true
            },
            confirmPassword: {
                required: true,
                space: true,
                equalTo: '#pass'
            }
        },
        messages:{
            password: {
                required: 'Mật khẩu không được để trống',
                space: 'Không được chứa khoảng trắng'
            },
            confirmPassword: {
                required: 'Xác nhận mật khẩu không được để trống',
                space: 'Không được chứa khoảng trắng',
                equalTo: 'Mật khẩu không khớp'
            }
        }
    });
});

document.addEventListener("DOMContentLoaded", function() {
   
    var path = window.location.pathname;

    var navLinks = document.querySelectorAll('.navbar-nav .nav-link');

    navLinks.forEach(function(link) {
        var href = link.getAttribute('href');

        if (href === "#") return;

        if (path === href) {
            link.classList.add('active');
        } else {
            link.classList.remove('active');
        }
    });
});

let currentIndex = 0;
let autoChangeTimer;

function changeImage(element, isAuto = false) {

    var newSrc = element.querySelector('img').src;
    var mainImg = document.getElementById('mainProductImg');
    if(mainImg) {
        mainImg.src = newSrc;
    }
    
    var thumbs = document.querySelectorAll('.thumb-box');
    thumbs.forEach(function(box, index) {
        box.classList.remove('border-primary', 'active-thumb');
        box.style.borderColor = '#dee2e6';
        
        if(box === element) {
            currentIndex = index;
        }
    });
    
    element.classList.add('border-primary');
    element.style.borderColor = '#0d6efd';

    if (!isAuto) {
        clearInterval(autoChangeTimer);
        startAutoChange();
    }
}

function startAutoChange() {
    var thumbs = document.querySelectorAll('.thumb-box');
 
    if(thumbs.length > 1) {
        autoChangeTimer = setInterval(function() {
            currentIndex++;
            if(currentIndex >= thumbs.length) {
                currentIndex = 0;
            }
            changeImage(thumbs[currentIndex], true);
        }, 3000); 
    }
}

document.addEventListener("DOMContentLoaded", function() {
    if (document.getElementById('mainProductImg')) {
        startAutoChange();
    }
});


jQuery.validator.addMethod('lettersonly', function(value, element) {
    return /^[^-\s][a-zA-Z_\s-]+$/.test(value);
});

jQuery.validator.addMethod('space', function(value, element) {
    return /^[^-\s]+$/.test(value);
});

jQuery.validator.addMethod('all', function(value, element) {
    return /^[^-\s][a-zA-Z0-9_,.\s-]+$/.test(value);
});

jQuery.validator.addMethod('numericOnly', function(value, element) {
    return /^[0-9]+$/.test(value);
});
