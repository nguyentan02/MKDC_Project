import { Component, OnInit, ViewChild, AfterViewInit, ViewEncapsulation } from '@angular/core';
import { PartnerService } from '../services/partner.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort, Sort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { CommonModule, NgIf } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';
import {MatButtonModule} from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-home',
  standalone: true,
  styleUrls: ['/home.component.css'],
  encapsulation: ViewEncapsulation.None ,
  imports: [CommonModule,MatButtonModule, MatTableModule, MatSortModule, MatPaginatorModule],
  template: `

<section>
  <div class="button-container">
   <input type="file" accept=".xlsx, .xls"  />
    <input type="file" (change)="onFileSelected($event)" accept=".xlsx, .xls" hidden  #fileInput />
    <button class="btn-import" mat-raised-button color="primary" (click)="fileInput.click()">Import File Excel</button>

  </div>
    <h1>Danh sách đối tác</h1>
  <table mat-table [dataSource]="dataSource" matSort class="mat-elevation-z8">

  <!-- No. Column -->
  <ng-container matColumnDef="position">
    <th mat-header-cell *matHeaderCellDef mat-sort-header>No.</th>
    <td mat-cell *matCellDef="let element; let i = index"> {{ i + 1 }} </td>
  </ng-container>

  <!-- Code Column -->
  <ng-container matColumnDef="code">
    <th mat-header-cell *matHeaderCellDef mat-sort-header>Mã</th>
    <td mat-cell *matCellDef="let element"> {{ element.code }} </td>
  </ng-container>

  <!-- Name Column -->
  <ng-container matColumnDef="name">
    <th mat-header-cell *matHeaderCellDef mat-sort-header>Tên</th>
    <td mat-cell *matCellDef="let element"> {{ element.name }} </td>
  </ng-container>

  <!-- Address Column -->
  <ng-container matColumnDef="address">
    <th mat-header-cell *matHeaderCellDef mat-sort-header>Địa chỉ</th>
    <td mat-cell *matCellDef="let element"> {{ element.address }} </td>
  </ng-container>

  <!-- Phone Number 1 Column -->
  <ng-container matColumnDef="phone_number_1">
    <th mat-header-cell *matHeaderCellDef mat-sort-header>Số điện thoại</th>
    <td mat-cell *matCellDef="let element"> {{ element.phoneNumber1 }} </td>
  </ng-container>

  <!-- Owner Name Column -->
  <ng-container matColumnDef="owner_Name">
    <th mat-header-cell *matHeaderCellDef mat-sort-header>Chủ sở hữu</th>
    <td mat-cell *matCellDef="let element"> {{ element.owner_Name }} </td>
  </ng-container>

  <!-- Tax Number Column -->
  <ng-container matColumnDef="tax_Number">
    <th mat-header-cell *matHeaderCellDef mat-sort-header>Mã số thuế</th>
    <td mat-cell *matCellDef="let element"> {{ element.taxNumber }} </td>
  </ng-container>

  <!-- City Name Column -->
  <ng-container matColumnDef="city_name">
    <th mat-header-cell *matHeaderCellDef mat-sort-header>Thành phố</th>
    <td mat-cell *matCellDef="let element"> {{ element.city?.name }} </td>
  </ng-container>

  <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
  <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
</table>

    <!-- Pagination -->
    <mat-paginator [pageSizeOptions]="[5, 10, 20]" showFirstLastButtons></mat-paginator>
  `,
  styles: [
    `
      table {
        width: 100%;
      }
     
    `
  ]
})
export class HomeComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['position', 'code', 'name', 'address', 'phone_number_1', 'owner_Name', 'tax_Number', 'city_name']
  dataSource = new MatTableDataSource([]);

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private partnerService: PartnerService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.partnerService.getPartners().subscribe({
      next: (response) => {
        console.log('API Response:', response);
        if (response && response.data) {
          this.dataSource.data = response.data;
        }
      },
      error: (error) => console.error('Lỗi gọi API:', error)
    });
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }
  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];

      const formData = new FormData();
      formData.append('file', file);

      this.partnerService.importExcel(formData).subscribe({
        next: (response) => {
          this.snackBar.open('Import thành công!', 'Đóng', { duration: 3000,horizontalPosition:'right',verticalPosition:'top' });
          this.ngOnInit(); // Load lại danh sách sau khi import thành công
        },
        error: (error) => {
          console.error('Lỗi khi import:', error);
          this.snackBar.open('Lỗi khi import dữ liệu!', 'Đóng', { duration: 3000 ,horizontalPosition:'right',verticalPosition:'top',panelClass:['error-snackbar']});
        }
      });
    }
  }
}
