/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.artemis.cli.commands.address;

import com.github.rvesse.airline.annotations.Command;
import org.apache.activemq.artemis.api.core.client.ClientMessage;
import org.apache.activemq.artemis.api.core.management.ManagementHelper;
import org.apache.activemq.artemis.cli.commands.AbstractAction;
import org.apache.activemq.artemis.cli.commands.ActionContext;

@Command(name = "update", description = "Update an address.")
public class UpdateAddress extends AddressAbstract {

   @Override
   public Object execute(ActionContext context) throws Exception {
      super.execute(context);
      updateAddress(context);
      return null;
   }

   private void updateAddress(final ActionContext context) throws Exception {
      performCoreManagement(new AbstractAction.ManagementCallback<ClientMessage>() {
         @Override
         public void setUpInvocation(ClientMessage message) throws Exception {
            ManagementHelper.putOperationInvocation(message, "broker", "updateAddress", getName(true), getRoutingTypes(false));
         }

         @Override
         public void requestSuccessful(ClientMessage reply) throws Exception {
            final String result = ManagementHelper.getResult(reply, String.class) + " updated successfully.";
            context.out.println(result);
         }

         @Override
         public void requestFailed(ClientMessage reply) throws Exception {
            String errMsg = (String) ManagementHelper.getResult(reply, String.class);
            context.err.println("Failed to update address " + getName(true) + ". Reason: " + errMsg);
         }
      });
   }
}
