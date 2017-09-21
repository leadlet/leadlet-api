package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EmailTemplates entity.
 */
public class EmailTemplatesDTO implements Serializable {

    private Long id;

    private String templateName;

    private String template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailTemplatesDTO emailTemplatesDTO = (EmailTemplatesDTO) o;
        if(emailTemplatesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailTemplatesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailTemplatesDTO{" +
            "id=" + getId() +
            ", templateName='" + getTemplateName() + "'" +
            ", template='" + getTemplate() + "'" +
            "}";
    }
}
