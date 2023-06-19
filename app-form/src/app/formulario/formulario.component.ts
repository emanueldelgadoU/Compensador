import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { FacturaService } from '../factura.service';


@Component({
  selector: 'app-formulario',
  templateUrl: './formulario.component.html',
  styleUrls: ['./formulario.component.css']
})
export class FormularioComponent {

  itemInvoices: any[] = [];
  secondItemInvoices: any[] = [];
  itemId!: number;

  totalAmountItemInvoices: number = 0;
  totalAmountSecondItemInvoices: number = 0;
  subtotalFacturasSeleccionadas: number = 0;
  allSelected: boolean = false;

  constructor(private route: ActivatedRoute, private facturaService: FacturaService) { }

  ngOnInit() {
    const userId = this.route.snapshot.queryParamMap.get('userId');
    if (userId) {
      this.facturaService.getFacturas(parseInt(userId)).subscribe((data: any) => {
        console.log(data);
        this.itemId = data.items[0].id;
        const secondItemId = data.items[1].id;
        data.itemInvoices.forEach((item: { itemId: number; }) => {
          if (item.itemId === this.itemId) {
            this.itemInvoices.push(item);
          } else if (item.itemId === secondItemId) {
            this.secondItemInvoices.push(item);
          }
        });
      });
    }
  }


  ///CALCULOS FORMULARIO///
  //TOTAL///
  calcularTotalAmount(): number {
    return this.itemInvoices.reduce((total, item) => total + item.amount, 0);
  }

  calcularImporteTotalDerecha(): number {
    return this.secondItemInvoices.reduce((total, item) => total + item.amount, 0);
  }


  ///SUBTOTAL//
  calcularSubtotalIzquierda() {
    let subtotal = 0;
    for (let item of this.itemInvoices) {
      if (item.selected) {
        subtotal += item.amount;
      }
    }
    return subtotal;
  }

  calcularSubtotalDerecha() {
    let subtotal = 0;
    for (let item of this.secondItemInvoices) {
      if (item.selected) {
        subtotal += item.amount;
      }
    }
    return subtotal;
  }

  igualarCompensacion() {

    if (this.calcularSubtotalDerecha() > this.calcularSubtotalIzquierda()) {
      return this.calcularSubtotalIzquierda()
    } else {
      return this.calcularSubtotalDerecha()
    }

  }

  balance() {
    if ((this.calcularSubtotalDerecha() - this.calcularSubtotalIzquierda()) <= 0) {
      return 0;
    } else {
      return this.calcularSubtotalDerecha() - this.calcularSubtotalIzquierda()
    }

  }

  ///BALANCE A PAGAR - COMPENSAR
  balancePagar() {
    return (this.calcularTotalAmount() - this.calcularSubtotalIzquierda());
  }

  remanenteCopensar() {
    return (this.calcularImporteTotalDerecha() - this.calcularSubtotalDerecha() + this.balance());
  }

  //SELECCIONAR TODAS
  seleccionarTodasFacturasIzquierda(event: any): void {
    this.itemInvoices.forEach(invoice => invoice.selected = event.target.checked);
  }

  seleccionarTodasFacturasDerecha(event: any): void {
    this.secondItemInvoices.forEach(invoice => invoice.selected = event.target.checked);
  }


  //metodo para objeres el json de assets y asi poder imprimirlas para
  // getPrueba(): Observable<any> {
  //   return this.http.get<any>('http://localhost:4200/assets/pruebaF.json');
  // }


  enviar() {
    const jsonEnvioDePueba = this.createJSON();
    console.log("hola");
    console.log(jsonEnvioDePueba);
  }


  async enviarBack() {
    const jsonEnvioDePrueba = await this.createJSON();
    console.log(jsonEnvioDePrueba);
    console.log(this.balance());

    this.facturaService.enviarFormulario(jsonEnvioDePrueba).subscribe(
      (response: any) => {
        console.log('Respuesta del backend:', response);
        // Realizar las operaciones adicionales que necesites con la respuesta del backend
      },
      (error: any) => {
        console.error('Error al enviar el JSON al backend:', error);
      }
    );
  }

  nuevaprueba(){
    return this.balance();
  }

  createJSON() {
    const userId = this.route.snapshot.queryParamMap.get('userId');
    let balancePagarValue = this.balancePagar();
    console.log(this.nuevaprueba())
    console.log(balancePagarValue)
    console.log(this.balancePagar())

    // JSON
    let jsonEnvioDePrueba = {
      "compensacion": [] as any[],
      "itemInvoices": [] as any[],
      "itemGhost": [] as any[],
      "transferencia": [] as any[],
    };

    // Agregar las facturas seleccionadas del primer grupo
    for (let invoice of this.itemInvoices) {
      if (invoice) {
        jsonEnvioDePrueba.itemInvoices.push(invoice)
      }
      if(invoice.selected) {
        jsonEnvioDePrueba.compensacion.push(invoice);
      }
    }

    // Agregar las facturas seleccionadas del segundo grupo
    for (let invoice of this.secondItemInvoices) {
      if (invoice.selected) {
        jsonEnvioDePrueba.itemInvoices.push(invoice);
      }
    }

    // Si el ID de usuario coincide con el ID de la factura, se agrega a itemGhost
    // Hay que poner el valor de BALANCE para que se actulice itemGhost


    console.log(this.nuevaprueba())
    if (userId) {
      for (let invoice of this.secondItemInvoices) {
        if (invoice.invoiceNum == userId) {
          invoice.amount = this.nuevaprueba();
          jsonEnvioDePrueba.itemGhost.push(invoice);
        }
      }
    }

    // Si balance a pagar > 0
    if (this.balancePagar() > 0) {
      jsonEnvioDePrueba.transferencia.push({
        importe: this.balancePagar(),
        id_usuario: userId
      });
    }

    return jsonEnvioDePrueba;
  }




}
