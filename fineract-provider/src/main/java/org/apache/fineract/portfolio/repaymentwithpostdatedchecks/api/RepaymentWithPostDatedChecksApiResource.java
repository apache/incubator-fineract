/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.LoanScheduleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/loans/{loanId}")
@Component
@Scope("singleton")
@Tag(name = "repayment with post dated checks", description = "Repay with post dated checks")
public class RepaymentWithPostDatedChecksApiResource {
    private final PlatformSecurityContext context;
    private final FromJsonHelper fromJsonHelper;
    private final DefaultToApiJsonSerializer<LoanScheduleData> loanScheduleToApiJsonSerializer;

    @Autowired
    public RepaymentWithPostDatedChecksApiResource(final PlatformSecurityContext context,
                                                   final FromJsonHelper fromJsonHelper,
                                                   final DefaultToApiJsonSerializer<LoanScheduleData> loanScheduleToApiJsonSerializer) {
        this.context = context;
        this.fromJsonHelper = fromJsonHelper;
        this.loanScheduleToApiJsonSerializer = loanScheduleToApiJsonSerializer;
    }

    @GET
    @Path("{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String getPostDatedChecks(@PathParam("id") @Parameter final Long id) {
        /**
         * TODO: Check the permission to read data.
         */
        this.context.authenticatedUser();

        return this.loanScheduleToApiJsonSerializer.serialize("");

    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON})
    public String addPostDatedChecks(@Parameter(hidden = true) final String apiRequestBodyAsJson) {
        return this.loanScheduleToApiJsonSerializer.serialize("");
    }


}
