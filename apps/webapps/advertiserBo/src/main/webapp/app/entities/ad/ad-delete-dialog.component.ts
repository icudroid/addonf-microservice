import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ad } from './ad.model';
import { AdPopupService } from './ad-popup.service';
import { AdService } from './ad.service';

@Component({
    selector: 'jhi-ad-delete-dialog',
    templateUrl: './ad-delete-dialog.component.html'
})
export class AdDeleteDialogComponent {

    ad: Ad;

    constructor(
        private adService: AdService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.adService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'adListModification',
                content: 'Deleted an ad'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ad-delete-popup',
    template: ''
})
export class AdDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adPopupService: AdPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.adPopupService
                .open(AdDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
