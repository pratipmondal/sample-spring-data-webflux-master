package pl.piomin.service.organization.mapper

import pl.piomin.service.organization.dto.OrganizationDTO

class EmployeeMapper {

    fun map(organizations: MutableList<OrganizationDTO>): OrganizationDTO {
        val org = OrganizationDTO(null, "")
        for (o in organizations) {
            if (o.id != null) org.id = o.id
            if (o.name != null) org.name = o.name
            if (o.employees != null) org.employees = o.employees
        }
        return org
    }
}