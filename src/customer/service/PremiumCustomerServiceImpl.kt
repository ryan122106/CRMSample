package com.example.crm.customer.service

import com.example.crm.app.Constant.CRMConstants
import com.example.crm.customer.model.Customer
import com.example.crm.customer.model.CustomerStatus
import com.example.crm.customer.repository.ICustomerRepository

class PremiumCustomerServiceImpl(private val customerRepository: ICustomerRepository) : CustomerService {
    override fun createCustomer(customer: Customer): Customer {
        validateCustomer(customer)
        return customerRepository.create(customer)
    }

    override fun updateCustomer(customer: Customer): Customer {
        validateCustomer(customer)
        return customerRepository.update(customer)
    }

    override fun findCustomerById(id: String): Customer {
        return customerRepository.findById(id)
            ?: throw IllegalArgumentException("Customer not found with id: $id")
    }

    override fun listCustomers(): List<Customer> {
        return customerRepository.findAll()
            .filter { it.isPremium }
    }

    override fun listActiveCustomers(): List<Customer> {
        return customerRepository.findAll()
            .filter { it.status == CustomerStatus.ACTIVE && it.isPremium }
    }

    private fun validateCustomer(customer: Customer) {
        require(customer.id.isNotBlank()) { CRMConstants.ERROR_CUSTOMER_ID_REQUIRED }
        require(customer.name.isNotBlank()) { CRMConstants.ERROR_CUSTOMER_NAME_REQUIRED }
        require(customer.phone.isNotBlank()) { CRMConstants.ERROR_CUSTOMER_PHONE_REQUIRED }
        require(customer.email.isNotBlank()) { CRMConstants.ERROR_CUSTOMER_EMAIL_REQUIRED }
        require(customer.status != null) { CRMConstants.ERROR_CUSTOMER_STATUS_REQUIRED }
        require(customer.createdAt != null) { CRMConstants.ERROR_CUSTOMER_CREATED_AT_REQUIRED }
    }
}