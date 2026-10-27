// ===================================================================
// Hiring Inquiry Form AJAX Submission & Validation Handler
// ===================================================================

document.addEventListener('DOMContentLoaded', function () {
    const hiringForm = document.getElementById('hiringInquiryForm');
    if (!hiringForm) return;

    const submitBtn = document.getElementById('submitInquiryBtn');
    const submitBtnSpinner = document.getElementById('submitBtnSpinner');
    const submitBtnText = document.getElementById('submitBtnText');
    const formAlert = document.getElementById('formAlert');
    const formAlertMessage = document.getElementById('formAlertMessage');

    // Utility to show alert
    function showAlert(type, message) {
        formAlert.className = `alert alert-${type} alert-dismissible fade show`;
        formAlertMessage.textContent = message;
        formAlert.classList.remove('d-none');
        formAlert.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    }

    function hideAlert() {
        formAlert.classList.add('d-none');
    }

    // Clear individual field error
    function clearFieldError(input) {
        input.classList.remove('is-invalid');
        const feedback = input.parentElement.querySelector('.invalid-feedback-custom');
        if (feedback) {
            feedback.style.display = 'none';
            feedback.textContent = '';
        }
    }

    // Set individual field error
    function setFieldError(input, message) {
        input.classList.add('is-invalid');
        const feedback = input.parentElement.querySelector('.invalid-feedback-custom');
        if (feedback) {
            feedback.style.display = 'block';
            feedback.textContent = message;
        }
    }

    // Reset all errors
    function resetAllErrors() {
        const inputs = hiringForm.querySelectorAll('.form-control-custom, .form-select-custom, .form-check-input');
        inputs.forEach(input => clearFieldError(input));
    }

    // Client-side quick validation
    function validateForm(formData) {
        let isValid = true;
        resetAllErrors();

        const fullName = hiringForm.querySelector('#fullName');
        if (!fullName.value.trim()) {
            setFieldError(fullName, 'Please enter your full name.');
            isValid = false;
        }

        const companyName = hiringForm.querySelector('#companyName');
        if (!companyName.value.trim()) {
            setFieldError(companyName, 'Please enter your company or organization name.');
            isValid = false;
        }

        const workEmail = hiringForm.querySelector('#workEmail');
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!workEmail.value.trim()) {
            setFieldError(workEmail, 'Work email is required.');
            isValid = false;
        } else if (!emailRegex.test(workEmail.value.trim())) {
            setFieldError(workEmail, 'Please enter a valid email address.');
            isValid = false;
        }

        const hiringType = hiringForm.querySelector('#hiringType');
        if (!hiringType.value || hiringType.value.trim() === '') {
            setFieldError(hiringType, 'Please select a hiring engagement type.');
            isValid = false;
        }

        const jobRole = hiringForm.querySelector('#jobRole');
        if (!jobRole.value.trim()) {
            setFieldError(jobRole, 'Please specify the job role or project title.');
            isValid = false;
        }

        const jobDescription = hiringForm.querySelector('#jobDescription');
        if (!jobDescription.value.trim()) {
            setFieldError(jobDescription, 'Please provide the job or project description.');
            isValid = false;
        }

        const consent = hiringForm.querySelector('#consent');
        if (!consent.checked) {
            setFieldError(consent, 'Consent is required to submit your inquiry.');
            isValid = false;
        }

        return isValid;
    }

    hiringForm.addEventListener('submit', async function (e) {
        e.preventDefault();
        hideAlert();

        // 1. Client Validation
        if (!validateForm()) {
            showAlert('warning', 'Please correct the highlighted fields before submitting.');
            return;
        }

        // 2. Prepare Payload
        const payload = {
            fullName: document.getElementById('fullName').value.trim(),
            companyName: document.getElementById('companyName').value.trim(),
            workEmail: document.getElementById('workEmail').value.trim(),
            contactNumber: document.getElementById('contactNumber').value.trim() || null,
            hiringType: document.getElementById('hiringType').value,
            jobRole: document.getElementById('jobRole').value.trim(),
            salaryBudget: document.getElementById('salaryBudget').value.trim() || null,
            jobDescription: document.getElementById('jobDescription').value.trim(),
            additionalMessage: document.getElementById('additionalMessage').value.trim() || null,
            consent: document.getElementById('consent').checked
        };

        // 3. Set Loading State (prevent double-submit)
        submitBtn.disabled = true;
        submitBtnSpinner.classList.remove('d-none');
        submitBtnText.textContent = 'Submitting Inquiry...';

        try {
            const response = await fetch('/api/hiring-inquiries', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            const data = await response.json();

            if (response.status === 201 && data.success) {
                // Backend confirmed persistence
                showAlert('success', data.message || 'Thank you for reaching out! Your hiring inquiry has been submitted successfully. I will review the details and get back to you.');
                hiringForm.reset();
                resetAllErrors();
            } else if (response.status === 400 && data.errors) {
                // Map backend validation errors inline
                let errorCount = 0;
                for (const [field, errorMsg] of Object.entries(data.errors)) {
                    const inputElement = hiringForm.querySelector(`#${field}`);
                    if (inputElement) {
                        setFieldError(inputElement, errorMsg);
                        errorCount++;
                    }
                }
                showAlert('danger', data.message || 'Validation failed. Please verify your input details.');
            } else {
                showAlert('danger', data.message || 'Unable to submit your hiring inquiry at this moment. Please try again.');
            }
        } catch (error) {
            console.error('Submission error:', error);
            showAlert('danger', 'Network error: Failed to reach the server. Please check your connection and try again.');
        } finally {
            // Restore button state
            submitBtn.disabled = false;
            submitBtnSpinner.classList.add('d-none');
            submitBtnText.textContent = 'Submit Hiring Inquiry';
        }
    });

    // Real-time input error clearing
    const formInputs = hiringForm.querySelectorAll('input, select, textarea');
    formInputs.forEach(input => {
        input.addEventListener('input', () => clearFieldError(input));
        input.addEventListener('change', () => clearFieldError(input));
    });
});
