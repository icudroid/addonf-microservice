import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AdRule } from './ad-rule.model';
import { AdRulePopupService } from './ad-rule-popup.service';
import { AdRuleService } from './ad-rule.service';

@Component({
    selector: 'jhi-ad-rule-delete-dialog',
    templateUrl: './ad-rule-delete-dialog.component.html'
})
export class AdRuleDeleteDialogComponent {

    adRule: AdRule;

    constructor(
        private adRuleService: AdRuleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.adRuleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'adRuleListModification',
                content: 'Deleted an adRule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ad-rule-delete-popup',
    template: ''
})
export class AdRuleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adRulePopupService: AdRulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.adRulePopupService
                .open(AdRuleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
