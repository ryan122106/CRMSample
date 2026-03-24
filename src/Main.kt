package com.example.crm.app

import com.example.crm.customer.model.Customer
import com.example.crm.customer.model.CustomerStatus
import com.example.crm.customer.repository.InMemoryCustomerRepository
import com.example.crm.customer.repository.InMemoryPremiumCustomerRepository
import com.example.crm.customer.service.CustomerServiceImpl
import com.example.crm.customer.service.PremiumCustomerServiceImpl
import com.example.crm.customer.usecase.CreateCustomerUseCase
import com.example.crm.customer.usecase.FindCustomerByIdUseCase
import com.example.crm.customer.usecase.ListActiveCustomersUseCase
import com.example.crm.customer.usecase.ListCustomersUseCase
import com.example.crm.lead.model.Lead
import com.example.crm.lead.model.LeadStatus
import com.example.crm.lead.repository.InMemoryLeadRepository
import com.example.crm.lead.service.LeadServiceImpl
import com.example.crm.lead.usecase.AssignLeadUseCase
import com.example.crm.lead.usecase.ConvertLeadToCustomerUseCase
import com.example.crm.lead.usecase.CreateLeadUseCase
import com.example.crm.lead.usecase.ListLeadsUseCase
import java.time.LocalDateTime

fun main() {
    val customerRepository = InMemoryPremiumCustomerRepository()
    val customerService = PremiumCustomerServiceImpl(customerRepository)
    val createCustomerUseCase = CreateCustomerUseCase(customerService)
    val findCustomerByIdUseCase = FindCustomerByIdUseCase(customerService)
    val listCustomersUseCase = ListCustomersUseCase(customerService)
    val listActiveCustomersUseCase = ListActiveCustomersUseCase(customerService)

    val leadRepository = InMemoryLeadRepository()
    val leadService = LeadServiceImpl(leadRepository, customerRepository, customerService)
    val createLeadUseCase = CreateLeadUseCase(leadService)
    val assignLeadUseCase = AssignLeadUseCase(leadService)
    val convertLeadToCustomerUseCase = ConvertLeadToCustomerUseCase(leadService)
    val listLeadsUseCase = ListLeadsUseCase(leadService)

    val lead1 = Lead(
        id = "1",
        firstName = "Jane",
        lastName = "Doe",
        contactInfo = "jane.doe@example.com",
        source = "Referral",
        status = LeadStatus.NEW,
        assignedSalesperson = null,
        createdAt = LocalDateTime.now(),
    )
    val createdLead1 = createLeadUseCase.execute(lead1)
    println("Created Lead 1: $createdLead1")

    val lead2 = Lead(
        id = "2",
        firstName = "Alice",
        lastName = "Johnson",
        contactInfo = "alice.johnson@example.com",
        source = "Email",
        status = LeadStatus.NEW,
        assignedSalesperson = null,
        createdAt = LocalDateTime.now(),
    )
    val createdLead2 = createLeadUseCase.execute(lead2)
    println("Created Lead 2: $createdLead2")

    val lead3 = Lead(
        id = "3",
        firstName = "Bob",
        lastName = "Smith",
        contactInfo = "bob.smith@example.com",
        source = "Social Media",
        status = LeadStatus.NEW,
        assignedSalesperson = null,
        createdAt = LocalDateTime.now()
    )
    val createdLead3 = createLeadUseCase.execute(lead3)
    println("Created Lead 3: $createdLead3")

    val assignedLead = assignLeadUseCase.execute("1", "salesperson1")
    println("Assigned Lead 1 to salesperson1: $assignedLead")

    val convertedCustomer = convertLeadToCustomerUseCase.execute("1")
    println("Converted Lead 1 to Customer: $convertedCustomer")

    val customer1 = Customer(
        id = "5",
        name = "Charlie Brown",
        phone = "987-654-3210",
        email = "charlie.brown@example.com",
        status = CustomerStatus.ACTIVE,
        createdAt = LocalDateTime.now(),
        isPremium = false
    )
    val createdCustomer1 = createCustomerUseCase.execute(customer1)
    println("Created Customer 1: $createdCustomer1")

    val customer2 = Customer(
        id = "2",
        name = "Diana Prince",
        phone = "555-555-5555",
        email = "diana.prince@example.com",
        status = CustomerStatus.ACTIVE,
        createdAt = LocalDateTime.now(),
        isPremium = true
    )
    val createdCustomer2 = createCustomerUseCase.execute(customer2)
    println("Created Customer 2: $createdCustomer2")

    val customer4 = Customer(
        id = "7",
        name = "Owi",
        phone = "555-555-5555",
        email = "diana.prince@example.com",
        status = CustomerStatus.ACTIVE,
        createdAt = LocalDateTime.now(),
        isPremium = true
    )
    val createdCustomer4 = createCustomerUseCase.execute(customer4)
    println("Created Customer 4: $createdCustomer4")

    val customer3 = Customer(
        id = "8",
        name = "Nic",
        phone = "555-555-5555",
        email = "nic@example.com",
        status = CustomerStatus.ACTIVE,
        createdAt = LocalDateTime.now(),
        isPremium = false
    )
    val createdCustomer3 = createCustomerUseCase.execute(customer3)
    println("Created Customer 3: $createdCustomer3")

    println("Logged activity for Lead 1")
    println("Logged activity for Customer 1")

    println("CRM Summary:")
    println("Customers Active:")
    listCustomersUseCase.execute().forEach { println(it) }

    println("Premium Customers:")
    listActiveCustomersUseCase.execute().forEach { println(it) }

    println("Leads:")
    listLeadsUseCase.execute().forEach { println(it) }
//
//    try {
//        val found = findCustomerByIdUseCase.execute("7")
//        println("Customer name: ${found.name}")
//    } catch (e: IllegalArgumentException) {
//        println(e.message)
//    }
}