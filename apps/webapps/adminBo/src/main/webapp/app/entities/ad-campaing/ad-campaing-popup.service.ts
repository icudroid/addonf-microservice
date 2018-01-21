import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AdCampaing } from './ad-campaing.model';
import { AdCampaingService } from './ad-campaing.service';

@Injectable()
export class AdCampaingPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private adCampaingService: AdCampaingService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.adCampaingService.find(id).subscribe((adCampaing) => {
                    adCampaing.start = this.datePipe
                        .transform(adCampaing.start, 'yyyy-MM-ddTHH:mm:ss');
                    adCampaing.end = this.datePipe
                        .transform(adCampaing.end, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.adCampaingModalRef(component, adCampaing);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.adCampaingModalRef(component, new AdCampaing());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    adCampaingModalRef(component: Component, adCampaing: AdCampaing): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.adCampaing = adCampaing;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
