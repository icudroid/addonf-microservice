/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdminBoTestModule } from '../../../test.module';
import { AdCampaingDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/ad-campaing/ad-campaing-delete-dialog.component';
import { AdCampaingService } from '../../../../../../main/webapp/app/entities/ad-campaing/ad-campaing.service';

describe('Component Tests', () => {

    describe('AdCampaing Management Delete Component', () => {
        let comp: AdCampaingDeleteDialogComponent;
        let fixture: ComponentFixture<AdCampaingDeleteDialogComponent>;
        let service: AdCampaingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [AdCampaingDeleteDialogComponent],
                providers: [
                    AdCampaingService
                ]
            })
            .overrideTemplate(AdCampaingDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdCampaingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdCampaingService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
