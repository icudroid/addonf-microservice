/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdvertiserBoTestModule } from '../../../test.module';
import { BrandUserDetailComponent } from '../../../../../../main/webapp/app/entities/brand-user/brand-user-detail.component';
import { BrandUserService } from '../../../../../../main/webapp/app/entities/brand-user/brand-user.service';
import { BrandUser } from '../../../../../../main/webapp/app/entities/brand-user/brand-user.model';

describe('Component Tests', () => {

    describe('BrandUser Management Detail Component', () => {
        let comp: BrandUserDetailComponent;
        let fixture: ComponentFixture<BrandUserDetailComponent>;
        let service: BrandUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [BrandUserDetailComponent],
                providers: [
                    BrandUserService
                ]
            })
            .overrideTemplate(BrandUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new BrandUser(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.brandUser).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
