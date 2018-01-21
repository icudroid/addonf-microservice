/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdvertiserBoTestModule } from '../../../test.module';
import { CustomerTargetDialogComponent } from '../../../../../../main/webapp/app/entities/customer-target/customer-target-dialog.component';
import { CustomerTargetService } from '../../../../../../main/webapp/app/entities/customer-target/customer-target.service';
import { CustomerTarget } from '../../../../../../main/webapp/app/entities/customer-target/customer-target.model';
import { BrandService } from '../../../../../../main/webapp/app/entities/brand';

describe('Component Tests', () => {

    describe('CustomerTarget Management Dialog Component', () => {
        let comp: CustomerTargetDialogComponent;
        let fixture: ComponentFixture<CustomerTargetDialogComponent>;
        let service: CustomerTargetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [CustomerTargetDialogComponent],
                providers: [
                    BrandService,
                    CustomerTargetService
                ]
            })
            .overrideTemplate(CustomerTargetDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerTargetDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerTargetService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CustomerTarget(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.customerTarget = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'customerTargetListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CustomerTarget();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.customerTarget = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'customerTargetListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
