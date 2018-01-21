import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AdCampaing } from './ad-campaing.model';
import { AdCampaingPopupService } from './ad-campaing-popup.service';
import { AdCampaingService } from './ad-campaing.service';

@Component({
    selector: 'jhi-ad-campaing-delete-dialog',
    templateUrl: './ad-campaing-delete-dialog.component.html'
})
export class AdCampaingDeleteDialogComponent {

    adCampaing: AdCampaing;

    constructor(
        private adCampaingService: AdCampaingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.adCampaingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'adCampaingListModification',
                content: 'Deleted an adCampaing'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ad-campaing-delete-popup',
    template: ''
})
export class AdCampaingDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adCampaingPopupService: AdCampaingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.adCampaingPopupService
                .open(AdCampaingDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
