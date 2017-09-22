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
package org.apache.fineract.infrastructure.bulkimport.populator;

import java.util.List;

import org.apache.fineract.organisation.office.data.OfficeData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class OfficeSheetPopulator extends AbstractWorkbookPopulator {

  private List<OfficeData> offices;

  private static final int ID_COL = 0;
  private static final int OFFICE_NAME_COL = 1;

  public OfficeSheetPopulator(final List<OfficeData> offices) {
    this.offices = offices;
  }


  @Override
  public void populate(final Workbook workbook) {
    int rowIndex = 1;
    Sheet officeSheet = workbook.createSheet("Offices");
    setLayout(officeSheet);

    populateOffices(officeSheet, rowIndex);
    officeSheet.protectSheet("");
  }

  private void populateOffices(Sheet officeSheet, int rowIndex) {
    for (OfficeData office : offices) {
      Row row = officeSheet.createRow(rowIndex);
      writeLong(ID_COL, row, office.getId());
      writeString(OFFICE_NAME_COL, row, office.name().trim().replaceAll("[ )(]", "_"));
      rowIndex++;
    }
  }

  private void setLayout(Sheet worksheet) {
    worksheet.setColumnWidth(ID_COL, 2000);
    worksheet.setColumnWidth(OFFICE_NAME_COL, 7000);
    Row rowHeader = worksheet.createRow(0);
    rowHeader.setHeight((short) 500);
    writeString(ID_COL, rowHeader, "ID");
    writeString(OFFICE_NAME_COL, rowHeader, "Name");
  }

  public List<OfficeData> getOffices() {
    return offices;
  }

}
